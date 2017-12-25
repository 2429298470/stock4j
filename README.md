# stock4j
股票数据采集、加工，用于股票指标量化分析  [主页](http://www.sigmagu.com/)

Tushare是有名的python财经数据接口包，但作为股票技术分析爱好者，长期以来采用Java语言的股票数据API很少，yahoo finance api由于某种原因，不能用于A股市场。 本api主要实现对股票数据的采集、加工、整理，方便金融分析人员的指标分析、回测等，极大地减轻在数据获取方面的工作量。

![stock4j](http://www.sigmagu.com/img/stock4j.jpg)

注意：本API并未得到新浪等财经网站的授权和使用，仅作为个人学习和交流。

>开始使用依赖的包：httpcore-4.4.4.jar，fluent-hc-4.5.2.jar，httpclient-4.5.2.jar，httpclient-cache-4.5.2.jar， httpclient-win-4.5.2.jar，httpmime-4.5.2.jar，jackson-annotations-2.9.0.jar， jackson-core-2.9.0.jar，jackson-databind-2.9.0.jar，commons-lang-2.4.jar， jsoup-1.9.2.jar，commons-logging-1.1.1.jar，slf4j-api-1.7.21.jar，ta4j-0.9.jar

### 行情数据
获取不同周期的行情数据，可以设定复权的方式 ，进行数据分析与回测，具体参考 mdeverdelhan/ta4j

```java
  Stock stock = new Stock("000001");
  StockFactory stockFactory = DefaultStockFactory.build();
  //PeriodType：数据周期，1分钟、5分钟、30分钟、日线、周线等
  //ExRightType：复权，前复权、后复权
  List<Tick> ticks = stockFactory.listTicks(stock, PeriodType.MIN5, 10, ExRightType.FORWARD);
```

### 盘口报价
获取5档盘口数据

```java
  Stock stock = new Stock("000001");
  //当前价，最高价、最低价、5档盘口报价
  Quote quote = DefaultStockFactory.build().getQuote(stock);
```

### 成交明细
获取当日的分钟成交数据，或者详细的分笔成交
```java
  Stock stock = new Stock("000001");
  StockFactory stockFactory = DefaultStockFactory.build();
  //时间、成交量、成交额、买卖方向
  LocalDateTime sdate = LocalDateTime.of(2017, 12, 22, 14, 23, 0);
  List<Transcation> transcations = stockFactory.listTranscations(stock, sdate, 10);
```

### 权息数据
获取股票的所有除权除息数据

```java
  Stock stock = new Stock("000001");
  StockFactory stockFactory = DefaultStockFactory.build();
  // 权息数据：日期,每股送股,每股配股,配股价,每股红利
  List<DividentRight> drs = stockFactory.listDividentRight(stock);
```

### 资金流向
获取股票的资金流向数据

```java
  Stock stock = new Stock("000001");
  StockFactory stockFactory = DefaultStockFactory.build();
  //主力流入、主力流出、散户流入、散户流出等
  List<CashFlow> cashFlows = stockFactory.listCashFlows(stock, 10);
```

### 其它数据
公司信息，股票自动补全
```java
  Stock stock = new Stock("000001");
  StockFactory stockFactory = DefaultStockFactory.build();
  //公司信息
  Company company = stockFactory.getCompanyInformation(stock);
  //股票自动补全
  List<Stock> stocks =  stockFactory.suggestStocks("60051");
```

数据主要来源于网络，如果在使用过程碰到数据无法获取或发生数据错误的情况请联系我，或者一起完善开发，感谢您的支持！
