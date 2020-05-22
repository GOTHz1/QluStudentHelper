package com.strong.qlu_studenthelper.location.schoolgate;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

public class MyGraph {
    public static Point[] points=new Point[]{
            //景点介绍
            //主节点
            new Point(0,0,"目前位置",9999),


            new Point(36.558968,116.817938,"南门（正门）",0),
            new Point(36.561314,116.818595,"机电楼",1),
            new Point(36.561343,116.817459,"实验楼",2),

            new Point(36.563234,116.818079,"图书馆",3),

            new Point(36.562705,116.817172,"美术馆",4),

            new Point(36.559879,116.821254,"食工楼",5),
            new Point(36.562651,116.821362,"一餐",6),
            new Point(36.568097,116.820271,"体育场东",7),
            new Point(36.569963,116.819521,"15-19学生公寓，体育场北",8),
            new Point(36.566716,116.816938,"三餐",9),
            new Point(36.568369,116.821587,"二餐",10),
            new Point(36.564571,116.815362,"1号公教楼",11),
            new Point(36.565419,116.815559,"2号公交楼",12),
            new Point(36.566238,116.815999,"3号公交楼",13),
            new Point(36.566097,116.814571,"20-25学生公寓",14),

            //辅助节点
            new Point(36.558962,116.818515,"位置01",1001),
            new Point(36.559045,116.817423,"位置02",1002),
            new Point(36.560643,116.817450,"位置03",1003),
            new Point(36.560622,116.818591,"位置04",1004),
            new Point(36.560480,116.821245,"位置05",1005),
            new Point(36.561336,116.817455,"位置06",1006),
            new Point(36.562238,116.817500,"位置07",1007),
            new Point(36.562241,116.817172,"位置08",1008),
            new Point(36.562223,116.818110,"位置09",1009),
            new Point(36.563292,116.817199,"位置10",1010),
            new Point(36.563734,116.817127,"位置11",1011),
            new Point(36.564017,116.816947,"位置12",1012),
            new Point(36.564216,116.816556,"位置13",1013),
            new Point(36.564296,116.815357,"位置14",1014),
            new Point(36.564647,116.814046,"位置15",1015),
            new Point(36.565463,116.814369,"位置99",1099),//后添加的点
            new Point(36.566666,116.814868,"位置16",1016),
            new Point(36.566807,116.815245,"位置17",1017),
            new Point(36.569629,116.817118,"位置18",1018),
            new Point(36.569970,116.818753,"位置19",1019),
            new Point(36.569561,116.820347,"位置20",1020),
            new Point(36.568680,116.820298,"位置21",1021),
            new Point(36.565919,116.820208,"位置98",1098),
            new Point(36.564227,116.820302,"位置22",1022),

    };
    //路径
    public static Edge[] edges=new Edge[]{
            new Edge(0,1002,10,2000),
            new Edge(0,1001,10,2000),
            new Edge(1001,1004,10,2001),
            new Edge(1002,1003,10,2002),
            new Edge(1003,1004,10,2003),
            new Edge(1003,1006,10,2004),
            new Edge(1004,1,10,2005),
            new Edge(1004,1005,10,2006),
            new Edge(1005,5,10,2000),
            new Edge(1005,1023,10,2000),
            new Edge(1006,2,10,2000),
            new Edge(1006,1,10,2000),
            new Edge(2,1007,10,2000),
            new Edge(1007,1008,10,2000),
            new Edge(1007,1009,10,2000),
            new Edge(1008,4,10,2000),
            new Edge(1009,3,10,2000),
            new Edge(1009,1023,10,2000),
            new Edge(4,1010,10,2000),
            new Edge(1010,3,10,2000),
            new Edge(1010,1011,10,2000),
            new Edge(1011,1012,10,2000),
            new Edge(1012,1013,10,2000),
            new Edge(1013,1014,10,2000),
            new Edge(1014,11,10,2000),
            new Edge(11,1015,10,2000),
            new Edge(1015,1099,10,2000),
            new Edge(1099,12,10,2000),
            new Edge(1099,14,10,2000),
            new Edge(14,13,10,2000),
            new Edge(14,1016,10,2000),
            new Edge(1016,1017,10,2000),
            new Edge(1017,9,10,2000),
            new Edge(9,1018,10,2000),
            new Edge(9,1098,10,2000),
            new Edge(1018,1019,10,2000),
            new Edge(1019,8,10,2000),
            new Edge(8,1020,10,2000),
            new Edge(1020,1021,10,2000),
            new Edge(1021,7,10,2000),
            new Edge(1021,10,10,2000),
            new Edge(7,1098,10,2000),
            new Edge(1098,1022,10,2000),
            new Edge(1022,6,10,2000),
            new Edge(6,1023,10,2000),


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
