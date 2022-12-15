package run;

import cs.nju.edu.Business;
import cs.nju.edu.Customer;
import cs.nju.edu.Movie;
import cs.nju.edu.User;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MovieSystem {

    public static final List<User> ALL_USERS = new ArrayList<>();
    public static final Map<Business, List<Movie>> ALL_MOVIES = new HashMap<>();

    public static User logInUser;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static final Logger LOGGER =  LoggerFactory.getLogger("MovieSystem.class");

    public static final Scanner SYS_SC = new Scanner(System.in);
    static {
        Customer c = new Customer();
        c.setLoginName("zyf888");
        c.setPassWord("123456");
        c.setUserName("这衣服");
        c.setSex('男');
        c.setMoney(10000);
        c.setPhone("110001");
        ALL_USERS.add(c);

        Customer c1 = new Customer();
        c1.setLoginName("api888");
        c1.setPassWord("123456");
        c1.setUserName("阿皮");
        c1.setSex('女');
        c1.setMoney(30000);
        c1.setPhone("112301");
        ALL_USERS.add(c1);

        Business b = new Business();
        b.setAddress("南大计算机系");
        b.setShopName("南大国际影城");
        b.setLoginName("qaq888");
        b.setPassWord("123456");
        b.setUserName("钱啊钱");
        b.setSex('女');
        b.setMoney(30000);
        b.setPhone("112301");
        List<Movie> l = new ArrayList<>();
        ALL_USERS.add(b);
        ALL_MOVIES.put(b, l);


        Business b1 = new Business();
        b1.setShopName("北大国际会议影院");
        b1.setAddress("北大计算机系");
        b1.setLoginName("dzj888");
        b1.setPassWord("123456");
        b1.setUserName("对自己");
        b1.setSex('男');
        b1.setMoney(30000);
        b1.setPhone("112301");
        List<Movie> l1 = new ArrayList<>();
        ALL_USERS.add(b1);
        ALL_MOVIES.put(b1, l1);




    }

    private static void showMain(){

        while(true){
            System.out.println("====================电影首页====================");
            System.out.println("1、登录");
            System.out.println("2、用户注册");
            System.out.println("3、商家注册");
            System.out.println("请输入操作命令：");
            String command = SYS_SC.nextLine();
            switch(command){
                case "1":
                    login();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                default:
                    System.out.println("命令有误，请确认！");
            }
        }

    }

    private static void login(){
        while(true){
            System.out.println("请输入登录名称：");
            String loginName = SYS_SC.nextLine();
            System.out.println("请输入登录密码：");
            String loginCode = SYS_SC.nextLine();

            User user = User_getUSerByLoginName(loginName);
            if( user!= null){
                if(user.getPassWord().equals(loginCode)){
                    System.out.println("登录成功！");
                    logInUser = user;
                    LOGGER.info(logInUser.getUserName()+"登陆了系统");
                    if (user instanceof Customer) {
                        //当前登录的为普通客户
                        showCustomer();
                    }
                    else{
                        //当前登录的为商家用户
                        showBusiness();
                    }
                    return; //离开while true循环
                }
                else{
                    System.out.println("密码错误请重试");
                }
            }else{
                System.out.println("登录名称错误，请确认");
            }
        }


    }

    private static void showBusiness(){

        while(true){
            System.out.println("====================电影商家首页====================");
            System.out.println(logInUser.getUserName() + (logInUser.getSex()=='男'?"先生":"女士")+"欢迎您进入系统");
            System.out.println("请您选择要操作的功能：");
            System.out.println("1、展示详情：");
            System.out.println("2、上架电影：");
            System.out.println("3、下架电影：");
            System.out.println("4、修改电影：");
            System.out.println("5、退出系统：");
            String command = SYS_SC.nextLine();
            switch(command){
                case "1":
                    showBusinessInfos();
                    break;
                case "2":
                    addMovie();
                    break;
                case "3":
                    deleteMovie();

                    break;
                case "4":
                    updataMovie();
                    break;
                case "5":
                    System.out.println("退出商家页面");
                    return;
                default:
                    System.out.println("命令有误，请确认！");
            }
        }
    }

    private static void deleteMovie() {
        Business business = (Business) logInUser;
        List<Movie> movies = ALL_MOVIES.get(logInUser);


        if(movies.size() == 0){
            System.out.println("当前无电影可以下架");
            return;
        }

        System.out.println("请您输入需要更新的电影名称：");
        String movieName = SYS_SC.nextLine();

        Movie movie = getMovieByName(movieName);
        if(movie != null){
            movies.remove(movie);
            System.out.println("您当前店铺已经成功下架了："+movie.getName());
        }
        else{
            System.out.println("您的店铺无该影片");
        }
        return;

    }

    public static Movie getMovieByName(String moveName){
        Business business = (Business) logInUser;
        List<Movie> movies = ALL_MOVIES.get(logInUser);
        for(Movie movie : movies){
            if(movie.getName().contains(moveName)){
                return movie;
            }
        }
        return null;
    }

    /**
     影片下架功能*/
    private static void updataMovie() {

        Business business = (Business) logInUser;
        List<Movie> movies = ALL_MOVIES.get(logInUser);


            if(movies.size() == 0){
                System.out.println("当前无电影可以下架");
                return;
            }

        System.out.println("请您输入需要下架的电影名称：");
        String movieName = SYS_SC.nextLine();

        Movie movie = getMovieByName(movieName);
        if(movie != null){
            System.out.println("请您输入修改后影片的信息新片名：");
            String name = SYS_SC.nextLine();
            System.out.println("请您输入主演：");
            String actor = SYS_SC.nextLine();
            System.out.println("请您输入时长：");
            String time = SYS_SC.nextLine();
            System.out.println("请您输入票价：");
            String price = SYS_SC.nextLine();
            System.out.println("请您输入票数：");
            String totalNumber = SYS_SC.nextLine();
            System.out.println("您当前店铺已经成功修改了："+movie.getName());

            try {
                System.out.println("请您输入影片放映时间：");
                String startTime = SYS_SC.nextLine();
                movie.setName(name);
                movie.setActor(actor);
                movie.setTime(Double.valueOf(time));
                movie.setNumber(Integer.valueOf(totalNumber));
                movie.setPrice(Double.valueOf(price));
                movie.setStartTime(sdf.parse(startTime));

                System.out.println("您已经成功修改了：《"+movie.getName()+"》");
                showBusinessInfos();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("时间解析错误");
            }

        }
        else{
            System.out.println("您的店铺无该影片");
        }
        return;


    }

    private static void showBusinessInfos(){
        Business business = (Business) logInUser;
        System.out.println(business.getShopName()+"\t\t电话："+business.getPhone() + "\t\t地址："+business.getAddress());
        List<Movie> movies = ALL_MOVIES.get(logInUser);
        System.out.println("片名\t\t\t主演\t\t时长\t\t评分\t\t票价\t\t余票数量\t\t放映时间");
        if(!movies.isEmpty()){
            for(Movie movie:movies){
                System.out.println(movie.getName() +"\t\t\t"+movie.getActor()+"\t\t"+movie.getTime()+"\t\t"+movie.getScore()+"\t\t"+movie.getPrice()+"\t\t"+movie.getNumber()+"\t\t"+sdf.format(movie.getStartTime()));
            }
        }
        else{
            System.out.println("您的店铺当前无片放映~");
        }

    }


    private static void addMovie() {
        Business business = (Business) logInUser;
        List<Movie> movies = ALL_MOVIES.get(business);


        while (true) {
            System.out.println("请您输入新片名：");
            String name = SYS_SC.nextLine();
            System.out.println("请您输入主演：");
            String actor = SYS_SC.nextLine();
            System.out.println("请您输入时长：");
            String time = SYS_SC.nextLine();
            System.out.println("请您输入票价：");
            String price = SYS_SC.nextLine();
            System.out.println("请您输入票数：");
            String totalNumber = SYS_SC.nextLine();
            System.out.println("请您输入影片放映时间：");
            String startTime = SYS_SC.nextLine();
            try {
                Movie movie = new Movie(name, actor, Double.valueOf(time), Double.valueOf(price), Integer.valueOf(totalNumber), sdf.parse(startTime));
                System.out.println("您已经成功上架了：《"+movie.getName()+"》");
                movies.add(movie);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("时间解析错误");
            }
        }
    }




    private static void showCustomer(){

        while(true){
            System.out.println("====================电影客户首页====================");
            System.out.println(logInUser.getUserName() + (logInUser.getSex()=='男'?"先生":"女士")+"欢迎您进入系统");
            System.out.println("请您选择要操作的功能：");
            System.out.println("1、展示全部影片信息功能：");
            System.out.println("2、根据电影名称查询电影信息：");
            System.out.println("3、评分功能：");
            System.out.println("4、购票功能：");
            System.out.println("5、退出系统：");
            String command = SYS_SC.nextLine();
            switch(command){
                case "1":
                    showAllMovies();

                    break;
                case "2":
                    queryMovies();
                    break;
                case "3":
                    scoreMovies();
                    break;
                case "4":
                    buyMovies();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("命令有误，请确认！");
            }
        }
    }

    private static void scoreMovies() {

        System.out.println("====================用户评分电影功能======================");
        Map<Business, List<Movie>> b_m = new HashMap<>();
        System.out.println("请您输入要查询的电影");
        String name = SYS_SC.nextLine();
        ALL_MOVIES.forEach((business, movies) -> {
            List<Movie> m = new ArrayList<>();
            System.out.println(business.getShopName()+"\t\t电话："+business.getPhone() + "\t\t地址："+business.getAddress());
            System.out.println("片名\t\t\t主演\t\t时长\t\t评分\t\t票价\t\t余票数量\t\t放映时间");
            for(Movie movie:movies){
                if(movie.getName().contains(name)){
                    System.out.println(movie.getName() +"\t\t\t"+movie.getActor()+"\t\t"+movie.getTime()+"\t\t"+movie.getScore()+"\t\t"+movie.getPrice()+"\t\t"+movie.getNumber()+"\t\t"+sdf.format(movie.getStartTime()));
                    m.add(movie);
                }
            }
            if(!m.isEmpty()){
                b_m.put(business, m);
            }
        });


        while(true){

            System.out.println("请您输入要评分的电影全名：");
            String movieName = SYS_SC.nextLine();
            b_m.forEach(((business, movies) -> {
                boolean flag = false;
                for(Movie movie:movies){
                    if(movie.getName().equals(movieName)){
                        flag = true;
                        System.out.println("请输入评分");
                        String score = SYS_SC.nextLine();

                        //对所有商家的同一部电影都起到作用
                        Set<Business> s = ALL_MOVIES.keySet();
                        for(Business b:s){
                            for(int i=0 ; i<ALL_MOVIES.get(b).size() ; ++i){
                                if(ALL_MOVIES.get(b).get(i).getName().equals(movieName)){
                                    ALL_MOVIES.get(b).get(i).setScoreNum(ALL_MOVIES.get(b).get(i).getScoreNum()+1);
                                    ALL_MOVIES.get(b).get(i).setScore((ALL_MOVIES.get(b).get(i).getScore()+Double.valueOf(score)) / ALL_MOVIES.get(b).get(i).getScoreNum());
                                    System.out.println("评分成功");
                                }

                            }
                        }
                    }
                }
                if(!flag){
                    System.out.println("没有找到对应的电影");
                    return;
                }

            }));
            System.out.println("输入EXIT退出评分系统，否则继续");
            String input = SYS_SC.nextLine();
            if(input.equals("EXIT")){
                return;
            }

        }







    }

    private static void queryMovies() {
        System.out.println("====================用户查询电影功能======================");

        System.out.println("请您输入要查询的电影");
        String name = SYS_SC.nextLine();
        ALL_MOVIES.forEach((business, movies) -> {

            System.out.println(business.getShopName()+"\t\t电话："+business.getPhone() + "\t\t地址："+business.getAddress());
            System.out.println("片名\t\t\t主演\t\t时长\t\t评分\t\t票价\t\t余票数量\t\t放映时间");
            for(Movie movie:movies){
                if(movie.getName().contains(name)){
                    System.out.println(movie.getName() +"\t\t\t"+movie.getActor()+"\t\t"+movie.getTime()+"\t\t"+movie.getScore()+"\t\t"+movie.getPrice()+"\t\t"+movie.getNumber()+"\t\t"+sdf.format(movie.getStartTime()));

                }
            }

        });
    }


    private static void buyMovies() {
        System.out.println("====================用户购票功能======================");
        Map<Business, List<Movie>> b_m = new HashMap<>();
        System.out.println("请您输入需要购买的电影");
        String name = SYS_SC.nextLine();
        ALL_MOVIES.forEach((business, movies) -> {
            List<Movie> m = new ArrayList<>();
            System.out.println(business.getShopName()+"\t\t电话："+business.getPhone() + "\t\t地址："+business.getAddress());
            System.out.println("片名\t\t\t主演\t\t时长\t\t评分\t\t票价\t\t余票数量\t\t放映时间");
            for(Movie movie:movies){
                if(movie.getName().contains(name)){
                    System.out.println(movie.getName() +"\t\t\t"+movie.getActor()+"\t\t"+movie.getTime()+"\t\t"+movie.getScore()+"\t\t"+movie.getPrice()+"\t\t"+movie.getNumber()+"\t\t"+sdf.format(movie.getStartTime()));
                    m.add(movie);
                }
            }
            if(!m.isEmpty()){
                b_m.put(business, m);
            }
        });


        while(true){

            System.out.println("请您输入要购买的门店全名：");
            String shopName = SYS_SC.nextLine();
            b_m.forEach(((business, movies) -> {
                boolean flag = false;
                if(business.getShopName().equals(shopName)){
                    System.out.println("请输入要购买的电影全名：");
                    String movieName = SYS_SC.nextLine();
                    for(Movie movie:movies){
                        if(movie.getName().equals(movieName)){
                            flag = true;
                            while(true) {

                                System.out.println("该门店上架的电影：" + movieName + "还剩" + movie.getNumber() + ",票价为" + movie.getPrice());
                                System.out.println("请输入要购买的票数：");
                                String num = SYS_SC.nextLine();
                                if(Integer.valueOf(num) > movie.getNumber()) {
                                    System.out.println("票数不足，请重试");
                                    System.out.println("如果要退出购票系统，请输入:EXIT");
                                    String input = SYS_SC.nextLine();
                                    if(input.equals("EXIT")) {
                                        return;
                                    }
                                }
                                else{
                                    System.out.println("购买成功");
                                    List<Movie> l = ALL_MOVIES.get(business);
                                    for (int i = 0; i < l.size(); i++) {
                                        if(movieName.equals(l.get(i).getName())){
                                            l.get(i).setNumber(l.get(i).getNumber()-Integer.valueOf(num));
                                            //ALL_MOVIES.get(business).get(i).setNumber(l.get(i).getNumber() - Integer.valueOf(num));
                                        }
                                    }
                                    return;
                                }

                            }

                        }
                    }

                }
                if(flag == false) {
                    System.out.println("无该影院或电影");
                    return ;

                }
            }));

            System.out.println("是否要退出系统？输入EXIT退出");
            String input = SYS_SC.nextLine();
            if(input.equals("EXIT")){
                return;
            }

            }
        
        
    }
    

    private static void showAllMovies() {
        ALL_MOVIES.forEach((business, movies) -> {
            System.out.println(business.getShopName()+"\t\t电话："+business.getPhone() + "\t\t地址："+business.getAddress());
            System.out.println("片名\t\t\t主演\t\t时长\t\t评分\t\t票价\t\t余票数量\t\t放映时间");
            List<Movie> l = ALL_MOVIES.get(business);

            l.sort(new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return (int) (o1.getScore() - o2.getScore());
                }
            });

            if(!l.isEmpty()){
                for(Movie movie:l){
                    System.out.println(movie.getName() +"\t\t\t"+movie.getActor()+"\t\t"+movie.getTime()+"\t\t"+movie.getScore()+"\t\t"+movie.getPrice()+"\t\t"+movie.getNumber()+"\t\t"+sdf.format(movie.getStartTime()));
                }
            }
        });

    }

    public static User User_getUSerByLoginName(String loginName){
        for (User user:ALL_USERS) {
            if(user.getLoginName().equals(loginName)){
                return user;
            }

        }
        return null;
    }

    public static void main(String[] args){
        showMain();

    }
}
