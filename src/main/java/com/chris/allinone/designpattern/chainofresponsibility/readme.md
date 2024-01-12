### 责任链模式
对同一对象的重复处理（比如HttpRequest），可考虑使用该模式。
> 实体：xx
> 
> 处理器接口：xxHandler -> handle(), sort()
> 
> 各种处理器接口实现：AxxHandler，BxxHandler，CxxHandler
> 
> 处理器调用链：组装各种处理器（Spring中可使用applicationContext.getBeansOfType()获取各种处理器）
>
handle()入参可灵活加入，比如带上xxHandlerChain，xxHandlerContext