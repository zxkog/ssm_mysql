package com.example.ssm_mysql.controller;

import com.example.ssm_mysql.domain.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.annotation.XmlRootElement;

@RestController
@RequestMapping(value = "/test")
public class Request {
    /**
     * URL,全称是UniformResourceLocator, 中文叫统一资源定位符,是互联网上用来标识某一处资源的地址。以下面这个URL为例，介绍下普通URL的各部分组成：
     * http://www.aspxfans.com:8080/news/index.asp?boardID=5&ID=24618&page=1#name
     * 从上面的URL可以看出，一个完整的URL包括以下几部分：
     * 协议部分：该URL的协议部分为“http：”，这代表网页使用的是HTTP协议。在Internet中可以使用多种协议，如HTTP，FTP等等本例中使用的是HTTP协议。
     * 在"HTTP"后面的“//”为分隔符
     * 域名部分：该URL的域名部分为“www.aspxfans.com”。一个URL中，也可以使用IP地址作为域名使用
     * 端口部分：跟在域名后面的是端口，域名和端口之间使用“:”作为分隔符。端口不是一个URL必须的部分，如果省略端口部分，将采用默认端口
     * 虚拟目录部分：从域名后的第一个“/”开始到最后一个“/”为止，是虚拟目录部分。虚拟目录也不是一个URL必须的部分。本例中的虚拟目录是“/news/”
     * 文件名部分：从域名后的最后一个“/”开始到“？”为止，是文件名部分，如果没有“?”,则是从域名后的最后一个“/”开始到“#”为止，是文件部分，
     * 如果没有“？”和“#”，那么从域名后的最后一个“/”开始到结束，都是文件名部分。本例中的文件名是“index.asp”。文件名部分也不是一个URL必须的部分，
     * 如果省略该部分，则使用默认的文件名
     * 锚部分：从“#”开始到最后，都是锚部分。本例中的锚部分是“name”。锚部分也不是一个URL必须的部分
     * 参数部分：从“？”开始到“#”为止之间的部分为参数部分，又称搜索部分、查询部分。本例中的参数部分为“boardID=5&ID=24618&page=1”。
     * 参数可以允许有多个参数，参数与参数之间用“&”作为分隔符。

     * 根据HTTP标准，HTTP请求可以使用多种请求方法。
     * HTTP1.0定义了三种请求方法： GET, POST 和 HEAD方法。
     * HTTP1.1新增了五种请求方法：OPTIONS, PUT, DELETE, TRACE 和 CONNECT 方法。
     *
     * GET      向特定的资源发出请求，获取资源，可以理解为读。
     * POST     向指定资源提交数据（表单或者文件），用于添加新的内容。 数据被包含在请求体中。POST请求可能会导致新的资源的建立和/或已有资源的修改。
     * HEAD     类似于get请求，只不过返回的响应中没有具体的内容，用于获取报头检查对象是否存在，并获取包含在响应消息头中的信息。
     * PUT      向指定资源位置上传其最新的内容，用于修改某个内容。
     * DELETE   请求服务器删除指定的页面。
     * CONNECT  HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器，用于代理进行传输，如使用ssl。
     * OPTIONS  获取服务器支持的HTTP请求方法；
     * TRACE    回显服务器收到的请求，主要用于测试或诊断。
     *
     * PUT/POST/PATCH方法前先引入一个概念，幂等，来帮助我们更好地理解这几种方法的区别。
     * 幂等idempotent :如果一个方法重复执行多次，产生的效果是一样的，那就是幂等的。幂等的意思是如果相同的操作再执行第二遍第三遍，结果还是一样。
     *
     * 关于POST/PUT/PATCH/的区别
     * POST：用来创建一个子资源
     * 如 /api/users，会在users下面创建一个user，如users/1；POST方法不是幂等的，多次执行，将导致多条相同的条目被创建。（比如在提交表单时刷新，会POST多个相同的表单给服务器）。重点：POST不是幂等的。
     *
     * PUT：比较正确的定义是Create or Update
     * 例如 PUT /items/1 的意思是替换 /items/1 ，存在则替换，不存在则创建。
     * 所以，PUT方法一般会用来更新一个已知资源。
     *
     * PATCH：是对PUT方法的补充，用来对已知资源进行局部更新，PATCH是幂等的。
     *
     * POST /api/articles
     * PUT /gists/:id/stars
     * 如果产生两个资源，就说明这个服务不是idempotent（幂等的），因为多次使用产生了副作用；如果后一个请求把第一个请求覆盖掉了，那这个服务就是idempotent的。
     * 前一种情况，应该使用POST方法；
     * 后一种情况，应该使用PUT方法。
     *
     *
     *HTTP之请求消息Request
     * 客户端发送一个HTTP请求到服务器的请求消息包括以下格式：
     * 请求行（request line）、请求头部（header）、空行、请求数据四个部分组成。
     * POST /login HTTP/1.1      请求行，用来说明请求类型,要访问的资源以及所使用的HTTP版本.
     * Host:www.wrox.com         请求头部，紧接着请求行（即第一行）之后的部分，用来说明服务器要使用的附加信息
     * User-Agent:Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022)
     * Content-Type:application/x-www-form-urlencoded
     * Content-Length:40
     * Connection: Keep-Alive
     *                          空行，请求头部后面的空行是必须的，即使第四部分的请求数据为空，也必须有空行。
     *                          下面的是主体，可以为空
     * name=Professional%20Ajax&publisher=Wiley
     *
     * HTTP之响应消息Response
     * 一般情况下，服务器接收并处理客户端发过来的请求后会返回一个HTTP的响应消息。
     * HTTP响应也由四个部分组成，分别是：状态行、消息报头、空行和响应正文。
     *
     * HTTP/1.1 200 OK                             状态行，由HTTP协议版本号， 状态码（例如200）， 状态消息为（ok）
     * Date: Fri, 22 May 2009 06:07:21 GMT         消息报头，用来说明客户端要使用的一些附加信息
     * Content-Type: text/html; charset=UTF-8
     *                                             空行
     * <html>                                      响应正文，服务器返回给客户端的文本信息。
     *       <head></head>
     *       <body>
     *
     *       </body>
     * </html>
     *
     * HTTP之状态码
     * 状态代码有三位数字组成，第一个数字定义了响应的类别，共分五种类别:
     * 1xx：指示信息--表示请求已接收，继续处理
     * 2xx：成功--表示请求已被成功接收、理解、接受
     * 3xx：重定向--要完成请求必须进行更进一步的操作
     * 4xx：客户端错误--请求有语法错误或请求无法实现
     * 5xx：服务器端错误--服务器未能实现合法的请求
     * 常见状态码：
     * 200 OK                        //客户端请求成功
     * 400 Bad Request               //客户端请求有语法错误，不能被服务器所理解
     * 401 Unauthorized              //请求未经授权，这个状态代码必须和WWW-Authenticate报头域一起使用
     * 403 Forbidden                 //服务器收到请求，但是拒绝提供服务
     * 404 Not Found                 //请求资源不存在，eg：输入了错误的URL
     * 500 Internal Server Error     //服务器发生不可预期的错误
     * 503 Server Unavailable        //服务器当前不能处理客户端的请求，一段时间后可能恢复正常
     * 更多状态码http://www.runoob.com/http/http-status-codes.html
     *
     * HTTP工作原理
     * HTTP协议采用了请求/响应模型。
     * 客户端向服务器发送一个请求报文，请求报文包含请求的方法、URL、协议版本、请求头部和请求数据。
     * 服务器以一个状态行作为响应，响应的内容包括协议的版本、成功或者错误代码、服务器信息、响应头部和响应数据。
     *
     * 以下是 HTTP 请求/响应的步骤：
     * 1、客户端连接到Web服务器
     * 一个HTTP客户端，通常是浏览器，与Web服务器的HTTP端口（默认为80）建立一个TCP套接字连接。例如，http://www.oakcms.cn。
     * 2、发送HTTP请求
     * 通过TCP套接字，客户端向Web服务器发送一个文本的请求报文，一个请求报文由请求行、请求头部、空行和请求数据4部分组成。
     * 3、服务器接受请求并返回HTTP响应
     * Web服务器解析请求，定位请求资源。服务器将资源复本写到TCP套接字，由客户端读取。一个响应由状态行、响应头部、空行和响应数据4部分组成。
     * 4、释放连接TCP连接
     * 若connection 模式为close，则服务器主动关闭TCP连接，客户端被动关闭连接，释放TCP连接;
     * 若connection 模式为keepalive，则该连接会保持一段时间，在该时间内可以继续接收请求;
     *
     *
     * 客户端浏览器解析HTML内容
     * 客户端浏览器首先解析状态行，查看表明请求是否成功的状态代码。然后解析每一个响应头，响应头告知以下为若干字节的HTML文档和文档的字符集。
     * 客户端浏览器读取响应数据HTML，根据HTML的语法对其进行格式化，并在浏览器窗口中显示。
     *
     * 例如：在浏览器地址栏键入URL，按下回车之后会经历以下流程：
     * 1、浏览器向 DNS 服务器请求解析该 URL 中的域名所对应的 IP 地址;
     * 2、解析出 IP 地址后，根据该 IP 地址和默认端口 80，和服务器建立TCP连接;
     * 3、浏览器发出读取文件(URL 中域名后面部分对应的文件)的HTTP 请求，该请求报文作为 TCP 三次握手的第三个报文的数据发送给服务器;
     * 4、服务器对浏览器请求作出响应，并把对应的 html 文本发送给浏览器;
     * 5、释放 TCP连接;
     * 6、浏览器将该 html 文本并显示内容;
     *
     *
     * GET和POST请求的区别
     *
     * GET请求
     * GET /books/?sex=man&name=Professional HTTP/1.1
     * Host: www.wrox.com
     * User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)
     * Gecko/20050225 Firefox/1.0.1
     * Connection: Keep-Alive
     *
     * 注意最后一行是空行
     *
     * POST请求
     * POST / HTTP/1.1
     * Host: www.wrox.com
     * User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)
     * Gecko/20050225 Firefox/1.0.1
     * Content-Type: application/x-www-form-urlencoded
     * Content-Length: 40
     * Connection: Keep-Alive
     *
     * name=Professional%20Ajax&publisher=Wiley
     *
     * GET提交，请求的数据会附在URL之后（就是把数据放置在HTTP协议头中），以?分割URL和传输数据，多个参数用&连接；
     *    例如：login.action?name=hyddd&password=idontknow&verify=%E4%BD%A0 %E5%A5%BD。
     *    如果数据是英文字母/数字，原样发送，如果是空格，转换为+，如果是中文/其他字符，则直接把字符串用BASE64加密，
     *    得出如： %E4%BD%A0%E5%A5%BD，其中％XX中的XX为该符号以16进制表示的ASCII。
     *
     * POST提交：把提交的数据放置在是HTTP包的包体中。上文示例中红色字体标明的就是实际的传输数据
     *     因此，GET提交的数据会在地址栏中显示出来，而POST提交，地址栏不会改变
     *
     * 2、传输数据的大小：首先声明：HTTP协议没有对传输的数据大小进行限制，HTTP协议规范也没有对URL长度进行限制。
     *     而在实际开发中存在的限制主要有：
     * GET:特定浏览器和服务器对URL长度有限制，例如 IE对URL长度的限制是2083字节(2K+35)。
     * 对于其他浏览器，如Netscape、FireFox等，理论上没有长度限制，其限制取决于操作系 统的支持。
     * 因此对于GET提交时，传输数据就会受到URL长度的 限制。
     *
     * POST:由于不是通过URL传值，理论上数据不受 限。但实际各个WEB服务器会规定对post提交数据大小进行限制，Apache、IIS6都有各自的配置。
     *
     * 3、安全性
     * POST的安全性要比GET的安全性高。比如：通过GET提交数据，用户名和密码将明文出现在URL上，因为(1)登录页面有可能被浏览器缓存；
     * (2)其他人查看浏览器的历史纪录，那么别人就可以拿到你的账号和密码了，除此之外，使用GET提交数据还可能会造成Cross-site request forgery攻击
     *
     * 4、Http get,post,soap协议都是在http上运行的
     * （1）get：请求参数是作为一个key/value对的序列（查询字符串）附加到URL上的
     * 查询字符串的长度受到web浏览器和web服务器的限制（如IE最多支持2048个字符），不适合传输大型数据集同时，它很不安全
     * （2）post：请求参数是在http标题的一个不同部分（名为entity body）传输的，这一部分用来传输表单信息，
     * 因此必须将Content-type设置为:application/x-www-form- urlencoded。post设计用来支持web窗体上的用户字段，其参数也是作为key/value对传输。
     * 但是：它不支持复杂数据类型，因为post没有定义传输数据结构的语义和规则。
     * （3）soap：是http post的一个专用版本，遵循一种特殊的xml消息格式
     * Content-type设置为: text/xml 任何数据都可以xml化。
     * Http协议定义了很多与服务器交互的方法，最基本的有4种，分别是GET,POST,PUT,DELETE. 一个URL地址用于描述一个网络上的资源，
     * 而HTTP中的GET, POST, PUT, DELETE就对应着对这个资源的查，改，增，删4个操作。 我们最常见的就是GET和POST了。GET一般用于获取/查询资源信息，
     * 而POST一般用于更新资源信息.
     *
     * 我们看看GET和POST的区别
     * 1.GET提交的数据会放在URL之后，以?分割URL和传输数据，参数之间以&相连，如EditPosts.aspx?name=test1&id=123456.
     *   POST方法是把提交的数据放在HTTP包的Body中.
     *
     * 2.GET提交的数据大小有限制（因为浏览器对URL的长度有限制），而POST方法提交的数据没有限制.
     * 3.GET方式需要使用Request.QueryString来取得变量的值，而POST方式通过Request.Form来获取变量的值。
     * 4.GET方式提交数据，会带来安全问题，比如一个登录页面，通过GET方式提交数据时，用户名和密码将出现在URL上，
     *   如果页面可以被缓存或者其他人可以访问这台机器，就可以从历史记录获得该用户的账号和密码.
     *
     *   RequestMapping注解有六个属性，下面我们把她分成三类进行说明。
     * 1、value， method
     *    value： 指定请求的实际地址，指定的地址可以是URI Template 模式（后面将会说明）
     *    method：指定请求的method类型，GET、POST、PUT、DELETE等
     * 2、consumes，produces
     *    consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
     *    produces:    指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
     * 3、params，headers
     *    params：指定request中必须包含某些参数值是，才让该方法处理。
     *    headers：指定request中必须包含某些指定的header值，才能让该方法处理请求。
     *
     *@ResponseBody
     *       @Responsebody 注解表示该方法的返回的结果直接写入 HTTP 响应正文（ResponseBody）中，一般在异步获取数据时使用，
     *       通常是在使用 @RequestMapping 后，返回值通常解析为跳转路径，加上 @Responsebody 后返回结果不会被解析为跳转路径，而是直接写入HTTP 响应正文中。
     * 作用：
     *     该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
     * 使用时机：
     *     返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用；
     *异步获取 json 数据，加上 @Responsebody 注解后，就会直接返回 json 数据。
     *
     * @RequestBody 注解则是将 HTTP 请求正文插入方法中，使用适合的 HttpMessageConverter 将请求体写入某个对象。
     */
    @RequestMapping(method = RequestMethod.GET)
    String GET () {
        return "Hello from GET，GET用来读数据";
    }

    @RequestMapping(method = RequestMethod.POST)
    String POST () {
        return "Hello from POST，POST用来添加修改数据";
    }

    @RequestMapping(method = RequestMethod.HEAD)
    String HEAD () {
        return "Hello from HEAD";
    }

    @RequestMapping(method = RequestMethod.PUT)
    String PUT () {
        return "Hello from PUT";
    }

    @RequestMapping(method = RequestMethod.PATCH)
    String PATCH () {
        return "Hello from PATCH";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    String DELETE () {
        return "Hello from DELETE";
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    String OPTIONS () {
        return "Hello from OPTIONS";
    }

    @RequestMapping(method = RequestMethod.TRACE)
    String delete () {
        return "Hello from delete";
    }

    /**
     * 访问的路径类似这样：localhost:8080/get1
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String IndexController(){
        return "Hello from IndexController  index";
    }

    /**
     * url传参，访问的路径类似这样：localhost:8080/getParamDemo1/1
     * 方法体中的参数要在前面加注释，@PathVariable 注解，其用来获取请求路径（url ）中的动态参数。
     */
    @RequestMapping(path = {"/get1/{id}"}, method = RequestMethod.GET)
    public String getParamDemo1 ( @PathVariable("id") int userId){
        System.out.println("get PathVariable userId " + userId);
        return "success get userId"+ userId;
    }

    /**
     * 当然，你也可以通过这种传参方式： localhost:8080/getParamDemo?param1=1 或者直接表单提交参数
     * 当然，同时方法中参数声明的注释也要变成@RequestParam，代表请求参数，required属性说明了参数是否是必须的
     */
    @RequestMapping(path = {"/get2"}, method = RequestMethod.GET)
    public String getParamDemo2 ( @RequestParam(value = "id", required = false) int userId){
        System.out.println("get RequestParam userId " + userId);
        return "success get userId"+ userId;
    }

    /**
     * 当然，你也可以通过这种传参方式： localhost:8080/getParamDemo?param1=1 或者直接表单提交参数
     * 当然，同时方法中参数声明的注释也要变成@RequestParam，代表请求参数，required属性说明了参数是否是必须的
     */
    @RequestMapping(path = {"/get3/{id}"}, method = RequestMethod.GET)
    public String getParamDemo3 (@PathVariable("id") int userId, @RequestParam(value = "token", required = false) String token){
        System.out.println("get PathVariable userId " + userId);
        System.out.println("get RequestParam token " + token);
        return "success get userId:" + userId + "   token:" + token;
    }

    /**
     * 对比两种传参
     * PathVariable：/get3/{id}   路径传参，如果有的参数是空值或者错位，会导致服务端找不到参数
     * RequestParam               键值传参，要对空值做处理，定位参数准确
     * 总结：传参的时候建议使用键值传参。传参也可以两种混合使用，但总感觉这样做特别sb
     */



    /**
     * localhost:8080/post  post提交的数据
     * ResponseBody适合异步获取数据
     */
    @RequestMapping(path = {"/post"}, method = RequestMethod.POST)
    @ResponseBody
    public User login1(@PathVariable("id") int userId, @RequestParam(value = "token", required = false) String token) {
        System.out.println("get PathVariable userId " + userId);
        System.out.println("get RequestParam token " + token);
        User user = new User();
        user.setId(userId);
        user.setUsername("serId");
        user.setPassword(token);
        user.setAge(10);
        return user ;
    }

    /**
     * localhost:8081/test/post2?token=abcdefg  post json提交的数据
     * ResponseBody适合异步获取数据
     * 可以接受json数据
     */
    @RequestMapping(path = {"/post1"}, method = RequestMethod.POST)
    @ResponseBody
    public User login2( @PathVariable("id") int userId, @RequestParam(value = "token", required = false) String token, @RequestBody User user) {
        System.out.println("get PathVariable userId " + userId);
        System.out.println("get RequestParam token " + token);
        user.setId(userId);
        user.setUsername("serId");
        user.setPassword(token);
        user.setAge(10);
        return user;
    }

    /**
     * localhost:8081/test/post2?token=abcdefg  post xml提交的数据
     * ResponseBody适合异步获取数据
     */
    @RequestMapping(path = {"/post3"}, method = RequestMethod.POST)
    @ResponseBody
    public User login3( @PathVariable("id") int userId, @RequestParam(value = "token", required = false) String token, @RequestBody String Sensor) {
        System.out.println("get PathVariable userId " + userId);
        System.out.println("get RequestParam token " + token);
        User user = new User();
        user.setId(userId);
        user.setUsername(Sensor);
        user.setPassword(token);
        user.setAge(10);
        return user;
//        @XmlRootElement(name ="Metrics");
//
//
//        // 每次doGet都生成一次context
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-anno.xml");
//        ApplicationContext context = new ClassPathXmlApplicationContext();
//
//
//        // 通过Spring IOC容器获取 service bean
//        userService service = context.getBean(userService.class);
//
//        // 调用service的方法，获取user
//        User user = service.findUser(Integer.parseInt(id));
//
//        // 响应，打印user
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().print(user);
    }
    /**
     * localhost:8080/getParamDemo?param1=1  路径传参，方法是delete
     */
    @RequestMapping(path = {"/deleteRequestDemo"}, method = RequestMethod.DELETE)
    public String deleteRequestDemo ( @RequestParam(value = "param1", required = false) int param){
        System.out.println("delete request test ,get param " + param);
        return "success get param";
    }

}





