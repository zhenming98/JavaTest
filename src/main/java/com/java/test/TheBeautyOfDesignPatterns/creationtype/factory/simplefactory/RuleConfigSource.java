package com.java.test.TheBeautyOfDesignPatterns.creationtype.factory.simplefactory;

/**
 * @author yzm
 * @date 2021/7/15 - 9:58
 */
public class RuleConfigSource {

}
//public class RuleConfigSource {
//    public RuleConfig load(String ruleConfigFilePath) {
//        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
//        IRuleConfigParser parser = null;
//        if ("json".equalsIgnoreCase(ruleConfigFileExtension)) {
//            parser = new JsonRuleConfigParser();
//        } else if ("xml".equalsIgnoreCase(ruleConfigFileExtension)) {
//            parser = new XmlRuleConfigParser();
//        } else if ("yaml".equalsIgnoreCase(ruleConfigFileExtension)) {
//            parser = new YamlRuleConfigParser();
//        } else if ("properties".equalsIgnoreCase(ruleConfigFileExtension)) {
//            parser = new PropertiesRuleConfigParser();
//        } else {
//            throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
//        }
//        String configText = "";
//        //从ruleConfigFilePath文件中读取配置文本到configText中
//        RuleConfig ruleConfig = parser.parse(configText);
//        return ruleConfig;
//    }
//    private String getFileExtension(String filePath) {
//        //...解析文件名获取扩展名，比如rule.json，返回json
//        return "json";
//    }
//}
/*
 * 在“规范和重构”那一部分中，我们有讲到，为了让代码逻辑更加清晰，可读性更好，我们要善于将功能独立的代码块封装成函数。
 * 按照这个设计思路，我们可以将代码中涉及 parser 创建的部分逻辑剥离出来，抽象成 createParser() 函数。
 * 重构之后的代码如下所示：
 */
//public class RuleConfigSource {
//    public RuleConfig load(String ruleConfigFilePath) {
//        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
//        IRuleConfigParser parser = createParser(ruleConfigFileExtension);
//        if (parser == null) {
//            throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
//        }
//        String configText = "";
//        //从ruleConfigFilePath文件中读取配置文本到configText中
//        RuleConfig ruleConfig = parser.parse(configText);
//        return ruleConfig;
//    }
//    private String getFileExtension(String filePath) {
//        //...解析文件名获取扩展名，比如rule.json，返回json
//        return "json";
//    }
//    private IRuleConfigParser createParser(String configFormat) {
//        IRuleConfigParser parser = null;
//        if ("json".equalsIgnoreCase(configFormat)) {
//            parser = new JsonRuleConfigParser();
//        } else if ("xml".equalsIgnoreCase(configFormat)) {
//            parser = new XmlRuleConfigParser();
//        } else if ("yaml".equalsIgnoreCase(configFormat)) {
//            parser = new YamlRuleConfigParser();
//        } else if ("properties".equalsIgnoreCase(configFormat)) {
//            parser = new PropertiesRuleConfigParser();
//        }
//        return parser;
//    }
//}
/*
 * 为了让类的职责更加单一、代码更加清晰，我们还可以进一步将 createParser() 函数剥离到一个独立的类中，
 * 让这个类只负责对象的创建。而这个类就是我们现在要讲的简单工厂模式类。具体的代码如下所示：
 */
//public class RuleConfigSource {
//    public RuleConfig load(String ruleConfigFilePath) {
//        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
//        IRuleConfigParser parser = RuleConfigParserFactory.createParser(ruleConfigFileExtension);
//        if (parser == null) {
//            throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
//        }
//        String configText = "";
//        //从ruleConfigFilePath文件中读取配置文本到configText中
//        RuleConfig ruleConfig = parser.parse(configText);
//        return ruleConfig;
//    }
//    private String getFileExtension(String filePath) {
//        //...解析文件名获取扩展名，比如rule.json，返回json
//        return "json";
//    }
//}
//
//public class RuleConfigParserFactory {
//    public static IRuleConfigParser createParser(String configFormat) {
//        IRuleConfigParser parser = null;
//        if ("json".equalsIgnoreCase(configFormat)) {
//            parser = new JsonRuleConfigParser();
//        } else if ("xml".equalsIgnoreCase(configFormat)) {
//            parser = new XmlRuleConfigParser();
//        } else if ("yaml".equalsIgnoreCase(configFormat)) {
//            parser = new YamlRuleConfigParser();
//        } else if ("properties".equalsIgnoreCase(configFormat)) {
//            parser = new PropertiesRuleConfigParser();
//        }
//        return parser;
//    }
//}
/*
 * 大部分工厂类都是以“Factory”这个单词结尾的，但也不是必须的，比如 Java 中的 DateFormat、Calender。
 * 除此之外，工厂类中创建对象的方法一般都是 create 开头，比如代码中的 createParser()，
 * 但有的也命名为 getInstance()、createInstance()、newInstance()，有的甚至命名为 valueOf()（比如 Java String 类的 valueOf() 函数）等等，
 * 这个我们根据具体的场景和习惯来命名就好。
 *
 * 在上面的代码实现中，我们每次调用 RuleConfigParserFactory 的 createParser() 的时候，都要创建一个新的 parser。
 * 实际上，如果 parser 可以复用，为了节省内存和对象创建的时间，我们可以将 parser 事先创建好缓存起来。
 * 当调用 createParser() 函数的时候，我们从缓存中取出 parser 对象直接使用。
 * 这有点类似单例模式和简单工厂模式的结合，具体的代码实现如下所示。
 *
 * 在接下来的讲解中，我们把上一种实现方法叫作简单工厂模式的第一种实现方法，把下面这种实现方法叫作简单工厂模式的第二种实现方法。
 */
//public class RuleConfigParserFactory {
//    private static final Map<String, RuleConfigParser> cachedParsers = new HashMap<>();
//
//    static {
//        cachedParsers.put("json", new JsonRuleConfigParser());
//        cachedParsers.put("xml", new XmlRuleConfigParser());
//        cachedParsers.put("yaml", new YamlRuleConfigParser());
//        cachedParsers.put("properties", new PropertiesRuleConfigParser());
//    }
//
//    public static IRuleConfigParser createParser(String configFormat) {
//        if (configFormat == null || configFormat.isEmpty()) {
//            //返回null还是IllegalArgumentException全凭你自己说了算
//            return null;
//        }
//        IRuleConfigParser parser = cachedParsers.get(configFormat.toLowerCase());
//        return parser;
//    }
//}

