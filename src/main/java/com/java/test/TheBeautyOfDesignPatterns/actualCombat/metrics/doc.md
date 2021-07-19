#### 25 | 实战二：针对非业务的通用框架开发，如何做需求分析和设计？

##### 功能性需求分析

相对于一大长串的文字描述，人脑更容易理解短的、罗列的比较规整、分门别类的列表信息。显然，刚才那段需求描述不符合这个规律。我们需要把它拆解成一个一个的“干条条”。拆解之后我写在下面了，是不是看起来更加清晰、有条理？

+ 接口统计信息：包括接口响应时间的统计信息，以及接口调用次数的统计信息等。
+ 统计信息的类型：max、min、avg、percentile、count、tps 等。
+ 统计信息显示格式：Json、Html、自定义显示格式。
+ 统计信息显示终端：Console、Email、HTTP 网页、日志、自定义显示终端。

##### 非功能性需求分析

+ 易用性
+ 性能
+ 扩展性
+ 容错性
+ 通用性

##### 框架设计

在原型系统的代码实现中，我们可以把所有代码都塞到一个类中，暂时不用考虑任何代码质量、线程安全、性能、扩展性等等问题，怎么简单怎么来就行。

```java
public class Metrics {
  // Map的key是接口名称，value对应接口请求的响应时间或时间戳；
  private Map<String, List<Double>> responseTimes = new HashMap<>();
  private Map<String, List<Double>> timestamps = new HashMap<>();
  private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

  public void recordResponseTime(String apiName, double responseTime) {
    responseTimes.putIfAbsent(apiName, new ArrayList<>());
    responseTimes.get(apiName).add(responseTime);
  }

  public void recordTimestamp(String apiName, double timestamp) {
    timestamps.putIfAbsent(apiName, new ArrayList<>());
    timestamps.get(apiName).add(timestamp);
  }

  public void startRepeatedReport(long period, TimeUnit unit){
    executor.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        Gson gson = new Gson();
        Map<String, Map<String, Double>> stats = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : responseTimes.entrySet()) {
          String apiName = entry.getKey();
          List<Double> apiRespTimes = entry.getValue();
          stats.putIfAbsent(apiName, new HashMap<>());
          stats.get(apiName).put("max", max(apiRespTimes));
          stats.get(apiName).put("avg", avg(apiRespTimes));
        }
  
        for (Map.Entry<String, List<Double>> entry : timestamps.entrySet()) {
          String apiName = entry.getKey();
          List<Double> apiTimestamps = entry.getValue();
          stats.putIfAbsent(apiName, new HashMap<>());
          stats.get(apiName).put("count", (double)apiTimestamps.size());
        }
        System.out.println(gson.toJson(stats));
      }
    }, 0, period, unit);
  }

  private double max(List<Double> dataset) {//省略代码实现}
  private double avg(List<Double> dataset) {//省略代码实现}
}
```

```java
//应用场景：统计下面两个接口(注册和登录）的响应时间和访问次数
public class UserController {
  private Metrics metrics = new Metrics();
  
  public UserController() {
    metrics.startRepeatedReport(60, TimeUnit.SECONDS);
  }

  public void register(UserVo user) {
    long startTimestamp = System.currentTimeMillis();
    metrics.recordTimestamp("regsiter", startTimestamp);
    //...
    long respTime = System.currentTimeMillis() - startTimestamp;
    metrics.recordResponseTime("register", respTime);
  }

  public UserVo login(String telephone, String password) {
    long startTimestamp = System.currentTimeMillis();
    metrics.recordTimestamp("login", startTimestamp);
    //...
    long respTime = System.currentTimeMillis() - startTimestamp;
    metrics.recordResponseTime("login", respTime);
  }
}
```

图可以非常直观地体现设计思想，并且能有效地帮助我们释放更多的脑空间，来思考其他细节问题。

<img src="https://static001.geekbang.org/resource/image/92/16/926561b82b49c937dcf4a2b9e6b35c16.jpg" alt="img" style="zoom:33%;" />

针对这个框架的开发，我们在 v1.0 版本中，暂时只实现下面这些功能。剩下的功能留在 v2.0、v3.0 版本，也就是我们后面的第 39 节和第 40 节课中再来讲解。

+ 数据采集：负责打点采集原始数据，包括记录每次接口请求的响应时间和请求时间。
+ 存储：负责将采集的原始数据保存下来，以便之后做聚合统计。数据的存储方式有很多种，我们暂时只支持 Redis 这一种存储方式，并且，采集与存储两个过程同步执行。
+ 聚合统计：负责将原始数据聚合为统计数据，包括响应时间的最大值、最小值、平均值、99.9 百分位值、99 百分位值，以及接口请求的次数和 tps。
+ 显示：负责将统计数据以某种格式显示到终端，暂时只支持主动推送给命令行和邮件。命令行间隔 n 秒统计显示上 m 秒的数据（比如，间隔 60s 统计上 60s 的数据）。邮件每日统计上日的数据。

##### 面向对象设计与实现

1. 划分职责进而识别出有哪些类

   根据需求描述，我们先大致识别出下面几个接口或类。这一步不难，完全就是翻译需求。

	+ MetricsCollector 类负责提供 API，来采集接口请求的原始数据。我们可以为 MetricsCollector 抽象出一个接口，但这并不是必须的，因为暂时我们只能想到一个 MetricsCollector 的实现方式。
	+ MetricsStorage 接口负责原始数据存储，RedisMetricsStorage 类实现 MetricsStorage 接口。这样做是为了今后灵活地扩展新的存储方法，比如用 HBase 来存储。
	+ Aggregator 类负责根据原始数据计算统计数据。
	+ ConsoleReporter 类、EmailReporter 类分别负责以一定频率统计并发送统计数据到命令行和邮件。至于 ConsoleReporter 和 EmailReporter 是否可以抽象出可复用的抽象类，或者抽象出一个公共的接口，我们暂时还不能确定。

2. 定义类及类与类之间的关系

   大致地识别出几个核心的类之后，我的习惯性做法是，先在 IDE 中创建好这几个类，然后开始试着定义它们的属性和方法。在设计类、类与类之间交互的时候，我会不断地用之前学过的设计原则和思想来审视设计是否合理，比如，是否满足单一职责原则、开闭原则、依赖注入、KISS 原则、DRY 原则、迪米特法则，是否符合基于接口而非实现编程思想，代码是否高内聚、低耦合，是否可以抽象出可复用代码等等。

   > 大致地识别出几个核心的类之后，我的习惯性做法是，**先在 IDE 中创建好这几个类，然后开始试着定义它们的属性和方法。**在设计类、类与类之间交互的时候，我会不断地用之前学过的设计原则和思想来审视设计是否合理，比如，是否满足单一职责原则、开闭原则、依赖注入、KISS 原则、DRY 原则、迪米特法则，是否符合基于接口而非实现编程思想，代码是否高内聚、低耦合，是否可以抽象出可复用代码等等。

   MetricsCollector 类的定义非常简单，具体代码如下所示。对比上一节课中最小原型的代码，MetricsCollector 通过引入 RequestInfo 类来封装原始数据信息，用一个采集函数代替了之前的两个函数。

   ```java
   public class MetricsCollector {
     private MetricsStorage metricsStorage;//基于接口而非实现编程
   
     //依赖注入
     public MetricsCollector(MetricsStorage metricsStorage) {
       this.metricsStorage = metricsStorage;
     }
   
     //用一个函数代替了最小原型中的两个函数
     public void recordRequest(RequestInfo requestInfo) {
       if (requestInfo == null || StringUtils.isBlank(requestInfo.getApiName())) {
         return;
       }
       metricsStorage.saveRequestInfo(requestInfo);
     }
   }
   
   public class RequestInfo {
     private String apiName;
     private double responseTime;
     private long timestamp;
     //...省略constructor/getter/setter方法...
   }
   ```

   MetricsStorage 类和 RedisMetricsStorage 类的属性和方法也比较明确。具体的代码实现如下所示。注意，一次性取太长时间区间的数据，可能会导致拉取太多的数据到内存中，有可能会撑爆内存。对于 Java 来说，就有可能会触发 OOM（Out Of Memory）。而且，即便不出现 OOM，内存还够用，但也会因为内存吃紧，导致频繁的 Full GC，进而导致系统接口请求处理变慢，甚至超时。这个问题解决起来并不难，先留给你自己思考一下。我会在第 40 节课中解答。

   ```java
   public interface MetricsStorage {
     void saveRequestInfo(RequestInfo requestInfo);
   
     List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis);
   
     Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis);
   }
   
   public class RedisMetricsStorage implements MetricsStorage {
     //...省略属性和构造函数等...
     @Override
     public void saveRequestInfo(RequestInfo requestInfo) {
       //...
     }
   
     @Override
     public List<RequestInfo> getRequestInfos(String apiName, long startTimestamp, long endTimestamp) {
       //...
     }
   
     @Override
     public Map<String, List<RequestInfo>> getRequestInfos(long startTimestamp, long endTimestamp) {
       //...
     }
   }
   ```

   实际上，如果我们把统计显示所要完成的功能逻辑细分一下的话，主要包含下面 4 点：

   + 根据给定的时间区间，从数据库中拉取数据；

   + 根据原始数据，计算得到统计数据；

   + 将统计数据显示到终端（命令行或邮件）；

   + 定时触发以上 3 个过程的执行。
   
   实际上，如果用一句话总结一下的话，**面向对象设计和实现要做的事情，就是把合适的代码放到合适的类中。**所以，我们现在要做的工作就是，把以上的 4 个功能逻辑划分到几个类中。划分的方法有很多种，
   
   比如，我们可以把前两个逻辑放到一个类中，第 3 个逻辑放到另外一个类中，第 4 个逻辑作为上帝类（God Class）组合前面两个类来触发前 3 个逻辑的执行。
   
   当然，我们也可以把第 2 个逻辑单独放到一个类中，第 1、3、4 都放到另外一个类中。
   
   至于到底选择哪种排列组合方式，判定的标准是，让代码尽量地满足低耦合、高内聚、单一职责、对扩展开放对修改关闭等之前讲到的各种设计原则和思想，尽量地让设计满足代码易复用、易读、易扩展、易维护。
   
   我们暂时选择把第 1、3、4 逻辑放到 ConsoleReporter 或 EmailReporter 类中，把第 2 个逻辑放到 Aggregator 类中。其中，Aggregator 类负责的逻辑比较简单，我们把它设计成只包含静态方法的工具类。具体的代码实现如下所示：
   
   ```java
   public class Aggregator {
     public static RequestStat aggregate(List<RequestInfo> requestInfos, long durationInMillis) {
       double maxRespTime = Double.MIN_VALUE;
       double minRespTime = Double.MAX_VALUE;
       double avgRespTime = -1;
       double p999RespTime = -1;
       double p99RespTime = -1;
       double sumRespTime = 0;
       long count = 0;
       for (RequestInfo requestInfo : requestInfos) {
         ++count;
         double respTime = requestInfo.getResponseTime();
         if (maxRespTime < respTime) {
           maxRespTime = respTime;
         }
         if (minRespTime > respTime) {
           minRespTime = respTime;
         }
         sumRespTime += respTime;
       }
       if (count != 0) {
         avgRespTime = sumRespTime / count;
       }
       long tps = (long)(count / durationInMillis * 1000);
       Collections.sort(requestInfos, new Comparator<RequestInfo>() {
         @Override
         public int compare(RequestInfo o1, RequestInfo o2) {
           double diff = o1.getResponseTime() - o2.getResponseTime();
           if (diff < 0.0) {
             return -1;
           } else if (diff > 0.0) {
             return 1;
           } else {
             return 0;
           }
         }
       });
       int idx999 = (int)(count * 0.999);
       int idx99 = (int)(count * 0.99);
       if (count != 0) {
         p999RespTime = requestInfos.get(idx999).getResponseTime();
         p99RespTime = requestInfos.get(idx99).getResponseTime();
       }
       RequestStat requestStat = new RequestStat();
       requestStat.setMaxResponseTime(maxRespTime);
       requestStat.setMinResponseTime(minRespTime);
       requestStat.setAvgResponseTime(avgRespTime);
       requestStat.setP999ResponseTime(p999RespTime);
       requestStat.setP99ResponseTime(p99RespTime);
       requestStat.setCount(count);
       requestStat.setTps(tps);
       return requestStat;
     }
   }
   
   public class RequestStat {
     private double maxResponseTime;
     private double minResponseTime;
     private double avgResponseTime;
     private double p999ResponseTime;
     private double p99ResponseTime;
     private long count;
     private long tps;
     //...省略getter/setter方法...
   }
   ```
   
   ConsoleReporter 类相当于一个上帝类，定时根据给定的时间区间，从数据库中取出数据，借助 Aggregator 类完成统计工作，并将统计结果输出到命令行。具体的代码实现如下所示：
   
   ```java
   public class ConsoleReporter {
     private MetricsStorage metricsStorage;
     private ScheduledExecutorService executor;
   
     public ConsoleReporter(MetricsStorage metricsStorage) {
       this.metricsStorage = metricsStorage;
       this.executor = Executors.newSingleThreadScheduledExecutor();
     }
     
     // 第4个代码逻辑：定时触发第1、2、3代码逻辑的执行；
     public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
       executor.scheduleAtFixedRate(new Runnable() {
         @Override
         public void run() {
           // 第1个代码逻辑：根据给定的时间区间，从数据库中拉取数据；
           long durationInMillis = durationInSeconds * 1000;
           long endTimeInMillis = System.currentTimeMillis();
           long startTimeInMillis = endTimeInMillis - durationInMillis;
           Map<String, List<RequestInfo>> requestInfos =
                   metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
           Map<String, RequestStat> stats = new HashMap<>();
           for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
             String apiName = entry.getKey();
             List<RequestInfo> requestInfosPerApi = entry.getValue();
             // 第2个代码逻辑：根据原始数据，计算得到统计数据；
             RequestStat requestStat = Aggregator.aggregate(requestInfosPerApi, durationInMillis);
             stats.put(apiName, requestStat);
           }
           // 第3个代码逻辑：将统计数据显示到终端（命令行或邮件）；
           System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMillis + "]");
           Gson gson = new Gson();
           System.out.println(gson.toJson(stats));
         }
       }, 0, periodInSeconds, TimeUnit.SECONDS);
     }
   }
   
   public class EmailReporter {
     private static final Long DAY_HOURS_IN_SECONDS = 86400L;
   
     private MetricsStorage metricsStorage;
     private EmailSender emailSender;
     private List<String> toAddresses = new ArrayList<>();
   
     public EmailReporter(MetricsStorage metricsStorage) {
       this(metricsStorage, new EmailSender(/*省略参数*/));
     }
   
     public EmailReporter(MetricsStorage metricsStorage, EmailSender emailSender) {
       this.metricsStorage = metricsStorage;
       this.emailSender = emailSender;
     }
   
     public void addToAddress(String address) {
       toAddresses.add(address);
     }
   
     public void startDailyReport() {
       Calendar calendar = Calendar.getInstance();
       calendar.add(Calendar.DATE, 1);
       calendar.set(Calendar.HOUR_OF_DAY, 0);
       calendar.set(Calendar.MINUTE, 0);
       calendar.set(Calendar.SECOND, 0);
       calendar.set(Calendar.MILLISECOND, 0);
       Date firstTime = calendar.getTime();
       Timer timer = new Timer();
       timer.schedule(new TimerTask() {
         @Override
         public void run() {
           long durationInMillis = DAY_HOURS_IN_SECONDS * 1000;
           long endTimeInMillis = System.currentTimeMillis();
           long startTimeInMillis = endTimeInMillis - durationInMillis;
           Map<String, List<RequestInfo>> requestInfos =
                   metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
           Map<String, RequestStat> stats = new HashMap<>();
           for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
             String apiName = entry.getKey();
             List<RequestInfo> requestInfosPerApi = entry.getValue();
             RequestStat requestStat = Aggregator.aggregate(requestInfosPerApi, durationInMillis);
             stats.put(apiName, requestStat);
           }
           // TODO: 格式化为html格式，并且发送邮件
         }
       }, firstTime, DAY_HOURS_IN_SECONDS * 1000);
     }
   }
   ```
   
3. 将类组装起来并提供执行入口

   因为这个框架稍微有些特殊，有两个执行入口：一个是 MetricsCollector 类，提供了一组 API 来采集原始数据；另一个是 ConsoleReporter 类和 EmailReporter 类，用来触发统计显示。框架具体的使用方式如下所示：

   ```java
   
   public class Demo {
     public static void main(String[] args) {
       MetricsStorage storage = new RedisMetricsStorage();
       ConsoleReporter consoleReporter = new ConsoleReporter(storage);
       consoleReporter.startRepeatedReport(60, 60);
   
       EmailReporter emailReporter = new EmailReporter(storage);
       emailReporter.addToAddress("wangzheng@xzg.com");
       emailReporter.startDailyReport();
   
       MetricsCollector collector = new MetricsCollector(storage);
       collector.recordRequest(new RequestInfo("register", 123, 10234));
       collector.recordRequest(new RequestInfo("register", 223, 11234));
       collector.recordRequest(new RequestInfo("register", 323, 12334));
       collector.recordRequest(new RequestInfo("login", 23, 12434));
       collector.recordRequest(new RequestInfo("login", 1223, 14234));
   
       try {
         Thread.sleep(100000);
       } catch (InterruptedException e) {
         e.printStackTrace();
       }
     }
   }
   ```

##### Review 设计与实现

我们前面讲到了 SOLID、KISS、DRY、YAGNI、LOD 等设计原则，基于接口而非实现编程、多用组合少用继承、高内聚低耦合等设计思想。我们现在就来看下，上面的代码实现是否符合这些设计原则和思想。

1. MetricsCollector

MetricsCollector 负责采集和存储数据，职责相对来说还算比较单一。它基于接口而非实现编程，通过依赖注入的方式来传递 MetricsStorage 对象，可以在不需要修改代码的情况下，灵活地替换不同的存储方式，满足开闭原则。

2. MetricsStorage、RedisMetricsStorage

MetricsStorage 和 RedisMetricsStorage 的设计比较简单。当我们需要实现新的存储方式的时候，只需要实现 MetricsStorage 接口即可。因为所有用到 MetricsStorage 和 RedisMetricsStorage 的地方，都是基于相同的接口函数来编程的，所以，除了在组装类的地方有所改动（从 RedisMetricsStorage 改为新的存储实现类），其他接口函数调用的地方都不需要改动，满足开闭原则。

3. Aggregator

Aggregator 类是一个工具类，里面只有一个静态函数，有 50 行左右的代码量，负责各种统计数据的计算。当需要扩展新的统计功能的时候，需要修改 aggregate() 函数代码，并且一旦越来越多的统计功能添加进来之后，这个函数的代码量会持续增加，可读性、可维护性就变差了。所以，从刚刚的分析来看，这个类的设计可能存在职责不够单一、不易扩展等问题，需要在之后的版本中，对其结构做优化。

4. ConsoleReporter、EmailReporter

ConsoleReporter 和 EmailReporter 中存在代码重复问题。在这两个类中，从数据库中取数据、做统计的逻辑都是相同的，可以抽取出来复用，否则就违反了 DRY 原则。而且整个类负责的事情比较多，职责不是太单一。特别是显示部分的代码，可能会比较复杂（比如 Email 的展示方式），最好是将显示部分的代码逻辑拆分成独立的类。除此之外，因为代码中涉及线程操作，并且调用了 Aggregator 的静态函数，所以代码的可测试性不好。

今天我们给出的代码实现还是有诸多问题的，在后面的章节（第 39、40 讲）中，我们会慢慢优化，给你展示整个设计演进的过程，这比直接给你最终的最优方案要有意义得多！实际上，优秀的代码都是重构出来的，复杂的代码都是慢慢堆砌出来的 。所以，当你看到那些优秀而复杂的开源代码或者项目代码的时候，也不必自惭形秽，觉得自己写不出来。毕竟罗马不是一天建成的，这些优秀的代码也是靠几年的时间慢慢迭代优化出来的。
