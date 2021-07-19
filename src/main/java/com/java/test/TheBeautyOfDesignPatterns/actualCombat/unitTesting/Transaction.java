package com.java.test.TheBeautyOfDesignPatterns.actualCombat.unitTesting;

/**
 * @author yzm
 * @date 2021/5/12 - 11:23
 */
public class Transaction {

}

/*
public class Transaction {
    private String id;
    private Long buyerId;
    private Long sellerId;
    private Long productId;
    private String orderId;
    private Long createTimestamp;
    private Double amount;
    private STATUS status;
    private String walletTransactionId;

    // ...get() methods...

    public Transaction(String preAssignedId, Long buyerId, Long sellerId, Long productId, String orderId) {
        if (preAssignedId != null && !preAssignedId.isEmpty()) {
            this.id = preAssignedId;
        } else {
            this.id = IdGenerator.generateTransactionId();
        }
        if (!this.id.startWith("t_")) {
            this.id = "t_" + preAssignedId;
        }
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.orderId = orderId;
        this.status = STATUS.TO_BE_EXECUTD;
        this.createTimestamp = System.currentTimeMillis();
    }

    public boolean execute() throws InvalidTransactionException {
        if ((buyerId == null || (sellerId == null || amount < 0.0))) {
            throw new InvalidTransactionException("...");
        }
        if (status == STATUS.EXECUTED) return true;
        boolean isLocked = false;
        try {
            isLocked = RedisDistributedLock.getSingletonIntance().lockTransction(id);
            if (!isLocked) {
                return false; // 锁定未成功，返回false，job兜底执行
            }
            if (status == STATUS.EXECUTED) return true; // double check
            long executionInvokedTimestamp = System.currentTimestamp();
            if (executionInvokedTimestamp - createdTimestap > 14days) {
                this.status = STATUS.EXPIRED;
                return false;
            }
            WalletRpcService walletRpcService = new WalletRpcService();
            String walletTransactionId = walletRpcService.moveMoney(id, buyerId, sellerId, amount);
            if (walletTransactionId != null) {
                this.walletTransactionId = walletTransactionId;
                this.status = STATUS.EXECUTED;
                return true;
            } else {
                this.status = STATUS.FAILED;
                return false;
            }
        } finally {
            if (isLocked) {
                RedisDistributedLock.getSingletonIntance().unlockTransction(id);
            }
        }
    }
}
*/
/*
在 Transaction 类中，主要逻辑集中在 execute() 函数中，所以它是我们测试的重点对象。为了尽可能全面覆盖各种正常和异常情况，针对这个函数，我设计了下面 6 个测试用例。

1.正常情况下，交易执行成功，回填用于对账（交易与钱包的交易流水）用的 walletTransactionId，交易状态设置为 EXECUTED，函数返回 true。
2.buyerId、sellerId 为 null、amount 小于 0，返回 InvalidTransactionException。
3.交易已过期（createTimestamp 超过 14 天），交易状态设置为 EXPIRED，返回 false。
4.交易已经执行了（status==EXECUTED），不再重复执行转钱逻辑，返回 true。
5.钱包（WalletRpcService）转钱失败，交易状态设置为 FAILED，函数返回 false。
6.交易正在执行着，不会被重复执行，函数直接返回 false。

测试用例设计完了。现在看起来似乎一切进展顺利。但是，事实是，当我们将测试用例落实到具体的代码实现时，你就会发现有很多行不通的地方。
对于上面的测试用例，第 2 个实现起来非常简单，我就不做介绍了。我们重点来看其中的 1 和 3。测试用例 4、5、6 跟 3 类似，留给你自己来实现。

测试用例1：
public void testExecute() {
  Long buyerId = 123L;
  Long sellerId = 234L;
  Long productId = 345L;
  Long orderId = 456L;
  Transction transaction = new Transaction(null, buyerId, sellerId, productId, orderId);
  boolean executedResult = transaction.execute();
  assertTrue(executedResult);
}

execute() 函数的执行依赖两个外部的服务，一个是 RedisDistributedLock，一个 WalletRpcService。这就导致上面的单元测试代码存在下面几个问题。
1.如果要让这个单元测试能够运行，我们需要搭建 Redis 服务和 Wallet RPC 服务。搭建和维护的成本比较高。
2.我们还需要保证将伪造的 transaction 数据发送给 Wallet RPC 服务之后，能够正确返回我们期望的结果，
   然而 Wallet RPC 服务有可能是第三方（另一个团队开发维护的）的服务，并不是我们可控的。换句话说，并不是我们想让它返回什么数据就返回什么。
3.Transaction 的执行跟 Redis、RPC 服务通信，需要走网络，耗时可能会比较长，对单元测试本身的执行性能也会有影响。
4.网络的中断、超时、Redis、RPC 服务的不可用，都会影响单元测试的执行。

我们回到单元测试的定义上来看一下。单元测试主要是测试程序员自己编写的代码逻辑的正确性，并非是端到端的集成测试，
它不需要测试所依赖的外部系统（分布式锁、Wallet RPC 服务）的逻辑正确性。
所以，如果代码中依赖了外部系统或者不可控组件，比如，需要依赖数据库、网络通信、文件系统等，
那我们就需要将被测代码与外部系统解依赖，而这种解依赖的方法就叫作“mock”。所谓的 mock 就是用一个“假”的服务替换真正的服务。mock 的服务完全在我们的控制之下，模拟输出我们想要的数据。
*/
