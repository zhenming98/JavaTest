package com.java.test.TheBeautyOfDesignPatterns.creationtype.factory.abstractfactory;

/**
 * @author yzm
 * @date 2021/7/15 - 10:18
 */
public class RuleConfigSource {
}
/*
 * 抽象工厂就是针对这种非常特殊的场景而诞生的。
 * 我们可以让一个工厂负责创建多个不同类型的对象（IRuleConfigParser、ISystemConfigParser 等），而不是只创建一种 parser 对象。
 * 这样就可以有效地减少工厂类的个数。具体的代码实现如下所示：
 */
//public interface IConfigParserFactory {
//    IRuleConfigParser createRuleParser();
//    ISystemConfigParser createSystemParser();
//    //此处可以扩展新的parser类型，比如IBizConfigParser
//}
//
//public class JsonConfigParserFactory implements IConfigParserFactory {
//    @Override
//    public IRuleConfigParser createRuleParser() {
//        return new JsonRuleConfigParser();
//    }
//
//    @Override
//    public ISystemConfigParser createSystemParser() {
//        return new JsonSystemConfigParser();
//    }
//}
//
//public class XmlConfigParserFactory implements IConfigParserFactory {
//    @Override
//    public IRuleConfigParser createRuleParser() {
//        return new XmlRuleConfigParser();
//    }
//
//    @Override
//    public ISystemConfigParser createSystemParser() {
//        return new XmlSystemConfigParser();
//    }
//}

// 省略YamlConfigParserFactory和PropertiesConfigParserFactory代码
