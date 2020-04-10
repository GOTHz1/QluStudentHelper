package com.strong.qlu_studenthelper.schoolgate;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

public class MyGraph {
    public static Point[] points=new Point[]{
            new Point(0,0,"目前位置",9999),//0
            //主节点
            new Point(36.5634730000,116.8109890000,"四号楼",0),//0
            new Point(36.5627250000,116.8110520000,"三号楼",1),//1
            new Point(36.5618850000,116.8125150000,"c区11",2),//2
            new Point(36.5654030000,116.8085130000,"汇文楼",3),//3R
            new Point(36.5643710000,116.8013180000,"主楼",4),//4
            new Point(36.5635500000,116.8066000000,"实验楼",5),//5
            new Point(36.5640850000,116.8055540000,"一号楼",6),//6

            /*
            2018.12.06新增景点
             */
            new Point(36.5652630000,116.8059250000,"体育场",210),
            new Point(36.5648190000,116.8149960000,"老图书馆",205),
            new Point(36.5661280000,116.8030600000,"A区游泳馆",213),
            new Point(36.5636360000,116.8026560000,"二号楼",203),
            new Point(36.5654330000,116.8008680000,"体育馆",211),
            new Point(36.5625888300,116.8179946500,"图书馆",206),
            /*
            11.27
             */
            //景点
            new Point(36.5632960000,116.8136580000,"艺术楼",26),
            new Point(36.5629940000,116.8137320000,"C区游泳馆",27),
            new Point(36.5625280000,116.8178820000,"C区冰场",28),
            new Point(36.5620470000,116.8142660000,"C区大门(南门)",29),
            new Point(36.5637050000,116.8119890000,"C区篮球场",30),
            new Point(36.5636110000,116.8167230000,"C区食堂",31),





            new Point(36.5633590000,116.8181060000,"C区29",7),
            new Point(36.5639510000,116.8182010000,"C区28",8),
            new Point(36.5635670000,116.8168220000,"C区27",9),
            new Point(36.5630100000,116.8164180000,"C区26",10),
            new Point(36.5623330000,116.8163280000,"C区25",11),
            new Point(36.5622550000,116.8152270000,"C区24",12),
            new Point(36.5636040000,116.8131300000,"C区23",13),
            new Point(36.5636610000,116.8139700000,"C区22",14),
            new Point(36.5639290000,116.8131480000,"C区21",15),
            new Point(36.5640920000,116.8142170000,"C区20",16),
            new Point(36.5643120000,116.8156900000,"C区19",17),
            new Point(36.5654890000,116.8176710000,"C区18",18),
            new Point(36.5652530000,116.8169210000,"C区16",19),
            new Point(36.5646900000,116.8178640000,"C区17",20),
            new Point(36.5628810000,116.8128380000,"C区15",21),
            new Point(36.5622950000,116.8122360000,"C区14",22),
            new Point(36.5625380000,116.8134260000,"C区13",23),
            new Point(36.5621000000,116.8133230000,"C区12",24),
            new Point(36.5620370000,116.8109150000,"C区10",25),




            //辅助节点
            new Point(36.5631950000,116.8109350000,"四号楼门口",3000),//7
            new Point(36.5628770000,116.8109850000,"三号楼门口",3001),//8
            new Point(36.5629590000,116.8103110000,"地下通道C区一侧",3002),//9
            new Point(36.5629840000,116.8100370000,"地下通道B区一侧",3003),//10
            new Point(36.5636420000,116.8097040000,"汇文楼东南侧拐角",3004),//11
            new Point(36.5636230000,116.8089810000,"汇文楼东南侧拐角靠左一处",3005),//12
            new Point(36.5654350000,116.8088780000,"汇文楼门口",3006),//13

            /*
            2018.11.29
            辅助节点
             */
            new Point(36.5621680000,116.8130460000,"c区",501),
            new Point(36.5620520000,116.8117390000,"c区",502),
            new Point(36.5619860000,116.8111460000,"c区",503),
            new Point(36.5623160000,116.8110790000,"c区",504),
            new Point(36.5638750000,116.8162420000,"c区",505),
            new Point(36.5632210000,116.8163640000,"c区",506),
            new Point(36.5639190000,116.8151420000,"c区",507),
            new Point(36.5652330000,116.8171020000,"c区",508),
            new Point(36.5650820000,116.8171380000,"c区",509),
            new Point(36.5646730000,116.8172730000,"c区",510),
            new Point(36.5642890000,116.8173360000,"c区",511),
            new Point(36.5643140000,116.8180630000,"c区",512),
            new Point(36.5636980000,116.8175150000,"c区",513),
            new Point(36.5635280000,116.8174970000,"c区",514),
            new Point(36.5641320000,116.8166350000,"c区",515),
            new Point(36.5630180000,116.8168780000,"c区",516),
            new Point(36.5644650000,116.8156740000,"c区",517),
            new Point(36.5640560000,116.8156740000,"c区",518),
            new Point(36.5635400000,116.8163650000,"c区",519),
            new Point(36.5630050000,116.8159250000,"c区",520),
            new Point(36.5626720000,116.8159160000,"c区",521),
            new Point(36.5641820000,116.8148520000,"c区",522),
            new Point(36.5633740000,116.8149280000,"c区",523),
            new Point(36.5627630000,116.8150670000,"c区",524),
            new Point(36.5638460000,116.8144930000,"c区",525),
            new Point(36.5633140000,116.8143980000,"c区",526),
            new Point(36.5622970000,116.8142100000,"c区",527),
            new Point(36.5637200000,116.8125610000,"c区",528),
            new Point(36.5634140000,116.8126110000,"c区",529),
            new Point(36.5630490000,116.8153190000,"c区",530),
            new Point(36.5633300000,116.8114880000,"c区",531),
            new Point(36.5629560000,116.8115350000,"c区",532),
            new Point(36.5627030000,116.8115950000,"c区",533),
            new Point(36.5627510000,116.8123630000,"c区",534),
            new Point(36.5622440000,116.8117030000,"c区",535),
            new Point(36.5623760000,116.8129880000,"c区",536),
            new Point(36.5628130000,116.8131940000,"c区",537),
            new Point(36.5622160000,116.8135990000,"c区",538),
            new Point(36.5630810000,116.8177260000,"c区",539),
            new Point(36.5636110000,116.8171320000,"c区",540),
            new Point(36.5643790000,116.8163140000,"c区",541),
            new Point(36.5637620000,116.8131300000,"c区",542),
            new Point(36.5638370000,116.8139430000,"c区",543),

            new Point(36.5633810000,116.8099430000,"b综北东北角",4000),//6
            new Point(36.5633240000,116.8088470000,"b综北西北角",4001),//6
            new Point(36.5632300000,116.8075090000,"b食堂西北角",4002),//6
            new Point(36.5630290000,116.8075400000,"b食堂左边",4003),//6
            new Point(36.5629750000,116.8069520000,"实验楼东南角",4004),//6
            new Point(36.5640480000,116.8067180000,"实验楼东北角",4005),//6
            new Point(36.5639190000,116.8055370000,"一号楼门口",4006),//6
            new Point(36.5638560000,116.8047420000,"校医院西北角",4007),//6
            new Point(36.5637080000,116.8030530000,"主楼东南角",4008),//6
            new Point(36.5635480000,116.8012660000,"主楼西南角",4009),//6
            new Point(36.5642620000,116.8011170000,"主楼门口",4010),//6
            new Point(36.5640140000,116.8062420000,"一号楼东南角",4011),//6
            new Point(36.5643750000,116.8062330000,"一号楼东北角",4012),//6
            new Point(36.5644100000,116.8072890000,"体育场下方",4013),//6

            new Point(36.5635600000,116.8068330000,"实验楼门口",4014),

            /*
            2018.12.06 辅助节点
             */
            new Point(36.5640170000,116.8062310000,"B",5015),
            new Point(36.5643910000,116.8062260000,"B",5016),
            new Point(36.5648880000,116.8057370000,"B",5017),
            new Point(36.5647620000,116.8058670000,"B",5022),
            new Point(36.5652220000,116.8056330000,"B",5009),
            new Point(36.5646680000,116.8050500000,"B",5004),
            new Point(36.5646430000,116.8045730000,"B",5018),
            new Point(36.5655990000,116.8043800000,"B",5019),
            new Point(36.5661280000,116.8043760000,"B",5020),
            new Point(36.5660240000,116.8030960000,"B",5014),
            new Point(36.5652690000,116.8008990000,"B",5012),
            new Point(36.5637180000,116.8030330000,"B",5001),
            new Point(36.5645450000,116.8028890000,"B",5002),

            new Point(36.5638560000,116.8047260000,"B",5021),
            new Point(36.5628050000,116.8048830000,"B",5008),
            new Point(36.5624910000,116.8049510000,"B",5007),

    };

    public static Edge[] edges=new Edge[]{
            new Edge(0,3000,10,0),
            new Edge(1,3001,10,1),
            new Edge(3000,3002,10,2),
            new Edge(3001,3002,10,3),
            new Edge(3002,3003,10,4),
            new Edge(3003,3004,10,5),
            new Edge(3004,3005,10,6),
            new Edge(3005,3006,10,7),
            new Edge(3006,3,10,8),

            /**2018.11.29
             * 景点加边
            */

            new Edge(18,508,10,9),
            new Edge(20,510,10,10),
            new Edge(508,509,10,11),
            new Edge(509,510,10,12),
            new Edge(19,508,10,13),
            new Edge(510,511,10,14),
            new Edge(8,512,10,15),
            new Edge(512,511,10,16),
            new Edge(7,539,10,17),
            new Edge(514,513,10,18),
            new Edge(513,511,10,19),
            new Edge(513,540,10,20),
            new Edge(514,540,10,21),
            new Edge(540,516,10,22),
            new Edge(540,515,10,23),
            new Edge(516,514,10,24),
            new Edge(513,515,10,25),
            new Edge(511,515,10,26),
            new Edge(515,505,10,27),
            new Edge(516,506,10,28),
            new Edge(519,506,10,29),
            new Edge(519,505,10,30),
            new Edge(505,518,10,31),
            new Edge(517,518,10,32),
            new Edge(517,17,10,33),
            new Edge(518,507,10,34),
            new Edge(507,523,10,35),
            new Edge(506,520,10,36),
            new Edge(520,530,10,37),
            new Edge(530,523,10,38),
            new Edge(523,26,10,39),
            new Edge(26,526,10,40),
            new Edge(517,522,10,41),
            new Edge(522,525,10,42),
            new Edge(522,16,10,43),
            new Edge(526,525,10,44),
            new Edge(524,526,10,45),
            new Edge(520,10,10,46),
            new Edge(524,521,10,47),
            new Edge(521,516,10,48),
            new Edge(12,521,10,49),
            new Edge(11,521,10,50),
            new Edge(12,11,10,51),
            new Edge(12,527,10,52),
            new Edge(527,524,10,53),
            new Edge(516,28,10,54),
            new Edge(11,28,10,55),
            new Edge(27,526,10,56),
            new Edge(529,27,10,57),
            new Edge(29,527,10,58),
            new Edge(527,538,10,59),
            new Edge(29,538,10,60),
            new Edge(527,537,10,61),
            new Edge(525,543,10,62),
            new Edge(4005,5015,10,63),
            new Edge(542,13,10,64),
            new Edge(542,15,10,65),
            new Edge(542,528,10,66),
            new Edge(4014,5,10,67),//实验楼测试用例
            new Edge(528,30,10,68),
            new Edge(528,529,10,69),
            new Edge(537,529,10,70),
            new Edge(529,531,10,71),
            new Edge(529,532,10,72),
            new Edge(532,533,10,73),
            new Edge(533,534,10,74),
            new Edge(533,535,10,75),
            new Edge(534,536,10,76),
            new Edge(535,536,10,77),
            new Edge(536,537,10,78),
            new Edge(538,536,10,79),
            new Edge(538,501,10,80),
            new Edge(538,24,10,81),
            new Edge(501,24,10,82),
            new Edge(501,502,10,83),
            new Edge(501,2,10,84),
            new Edge(502,2,10,85),
            new Edge(501,536,10,86),
            new Edge(535,502,10,87),
            new Edge(504,25,10,88),
            new Edge(502,503,10,90),
            new Edge(503,25,10,91),
            new Edge(504,503,10,92),
            new Edge(535,504,10,93),
            new Edge(30,531,10,94),
            new Edge(3001,532,10,95),
            new Edge(3000,531,10,96),
            new Edge(3001,3000,10,97),
            new Edge(514,539,10,98),
            new Edge(516,539,10,99),
            new Edge(9,540,10,100),
            new Edge(515,31,10,101),
            new Edge(541,31,10,102),
            new Edge(517,541,10,103),
            new Edge(515,541,10,104),
            new Edge(21,534,10,105),
            new Edge(16,543,10,106),
            new Edge(14,543,10,107),
            new Edge(542,543,10,108),

            new Edge(3003,4000,10,0),
            new Edge(4000,4001,10,1),
            new Edge(4001,4002,10,2),
            new Edge(4002,4003,10,3),
            new Edge(4003,4004,10,4),
            new Edge(4004,4014,10,5),
            new Edge(4005,4011,6,6),
            new Edge(4006,4011,6,6),
            new Edge(4006,6,10,7),
            new Edge(4006,4007,10,8),
            new Edge(4007,4008,10,0),
            new Edge(4008,4009,10,1),
            new Edge(4009,4010,10,2),
            new Edge(4010,4,10,3),
            new Edge(4011,4012,10,4),
            new Edge(4012,4013,10,5),
            new Edge(4013,3005,10,6),
            new Edge(4005,4014,10,7),


            new Edge(4005,4014,10,11091),
            new Edge(5015,5016,10,11092),
            new Edge(5016,5022,10,11093),
            new Edge(5022,5017,10,11094),
            new Edge(5017,5009,10,11095),
            new Edge(5009,210,10,11096),
            new Edge(5022,5004,10,11097),
            new Edge(5004,205,10,11098),
            new Edge(5004,5018,10,11099),
            new Edge(5018,5019,10,110910),
            new Edge(5019,5020,10,110911),
            new Edge(5020,5014,10,110912),
            new Edge(5014,213,10,110913),
            new Edge(5019,5012,10,110914),
            new Edge(5012,211,10,110915),
            new Edge(5015,5001,10,110916),
            new Edge(5001,5002,10,110917),
            new Edge(5002,203,10,110918),


            new Edge(5015,5021,10,110918),
            new Edge(5008,5021,10,110918),
            new Edge(5007,5008,10,110918),
            new Edge(5007,206,10,110918),
            new Edge(4004,5008,10,110918),



    };

    private int startArrayIndex;
    private int endArrayIndex;
    private static double distance;

    private class Node{
        int to;
        double dis;
        public Node(int to, double dis) {
            this.to = to;
            this.dis = dis;
        }
    }

    public MyGraph(int startIndex, int endIndex) {
        this.startArrayIndex = getArrayIndex(startIndex);
        this.endArrayIndex = getArrayIndex(endIndex);
    }

    public static int getArrayIndex(int index){
        for(int i=0;i<points.length;i++){
            if(index==points[i].index){
                return i;
            }
        }
        return 0;
    }

    public static Comparator<Node> comparator = new Comparator<Node>(){
        @Override
        public int compare(Node n1, Node n2) {
            return (int)(n1.dis-n2.dis);
        }
    };

    private void link(Vector<Node>[] G){
        Point temp=null;
        double minn=Double.MAX_VALUE;
        Point startPoint=points[startArrayIndex];
        for(int i=1;i<points.length;i++){
            double dis= DistanceUtil.getDistance(new LatLng(startPoint.latitude,startPoint.longitude),new LatLng(points[i].latitude,points[i].longitude));
            if(dis<minn){
                minn=dis;
                temp=points[i];
            }
        }
        G[startArrayIndex].add(new Node(getArrayIndex(temp.index),10));
        G[getArrayIndex(temp.index)].add(new Node(startArrayIndex,10));
    }

    private double getDistance(Edge e){
        Point p1=points[getArrayIndex(e.from)];
        Point p2=points[getArrayIndex(e.to)];
        return DistanceUtil.getDistance(new LatLng(p1.latitude,p1.longitude),new LatLng(p2.latitude,p2.longitude));
    }

    public static double getDistance() {
        return distance;
    }

    public Point[] dijkstra(boolean flag){
        long time=System.currentTimeMillis();
        Vector<Node>[] G=new Vector[points.length];
        for(int i=0;i<points.length;i++)
            G[i]=new Vector<>();
        for(Edge e:edges) {
            double dis=getDistance(e);
            G[getArrayIndex(e.from)].add(new Node(getArrayIndex(e.to), dis));
            G[getArrayIndex(e.to)].add(new Node(getArrayIndex(e.from), dis));
        }
        if(flag) link(G);
        double[] d=new double[points.length];

        for(int i=0;i<d.length;i++)
            d[i]=Double.MAX_VALUE;
        d[startArrayIndex]=0;

        int[] pre=new int[points.length];

        PriorityQueue<Node> q=new PriorityQueue<>(999,comparator);
        q.add(new Node(startArrayIndex,0));
        while (!q.isEmpty()){
            Node t=q.remove();
            int v=t.to;
            if(d[v]< t.dis)continue;
            for(Node u:G[v]){
                if(d[u.to]>d[v]+u.dis){
                    d[u.to]=d[v]+u.dis;
                    q.add(new Node(u.to,d[u.to]));
                    pre[u.to]=v;
                }
            }
        }

        distance=d[endArrayIndex];

        Vector<Point> path=new Vector<>();
        int temp=endArrayIndex;
        while(temp!=startArrayIndex){
            path.add(points[temp]);
            temp=pre[temp];
        }
        path.add(points[temp]);

        Point[] tempPath=new Point[path.size()];
        return path.toArray(tempPath);
    }
}
