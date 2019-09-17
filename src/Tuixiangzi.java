import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.sound.midi.*;
import java.net.URI;
import java.net.URL;
import java.util.Stack;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URI;
import java.net.URL;

public class Tuixiangzi
{
    public static void main(String[] args)
    {
        new Members();
    }
}
 class Members extends JFrame implements KeyListener,ActionListener{
     Sound sound;

     //菜单栏下拉选项
     JMenuItem renew=new JMenuItem("重新开始");//第一个
     JMenuItem last=new JMenuItem("上一关");
     JMenuItem next=new JMenuItem("下一关");
     JMenuItem choose=new JMenuItem("退出");

     JMenuItem nor=new JMenuItem("默认");
     JMenuItem item2_2=new JMenuItem("泡泡堂");
     JMenuItem item2_3=new JMenuItem("灌篮高手");
     JMenuItem item2_4=new JMenuItem("琴箫合奏");
     JMenuItem item2_5=new JMenuItem("喜羊羊");
     JMenuItem item2_6=new JMenuItem("关音乐");

     JMenuItem item3_1=new JMenuItem("背景故事");
     JMenuItem about=new JMenuItem("关于推箱子");



    //定义一个JLabel数组，用来存放羊的位置
    JLabel [][]sheep = new JLabel[12][16];

    //0表示的是空地，1表示的是树木
    int[][] datas = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,0,0,0,0,0,0,1,1,1,0,0,0,1},
            {1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,1,1,0,1,1,0,0,1},
            {1,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1},
            {1,0,0,0,1,0,0,1,1,0,0,0,0,0,0,1},
            {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    //狼的位置
    int wx,wy;
    /*
     * num值变化的情况
     * 1.当羊进入笼子的时候，num+1
     * 2.当羊离开笼子的时候，num-1
     * 3.当羊从一个笼子离开进入另外一个笼子的时候，num不变
     */
    //开始的时候羊进入箱子的总数量
    int num = 0;
    //笼子的总数量
    int total = 3;

    //构造函数
    public Members()
    {


        sound=new Sound();
        sound.loadSound();

//菜单栏
        JMenuBar jmb=new JMenuBar();
        //设置菜单栏选项
        JMenu menu1=new JMenu("选项");
        JMenu menu2=new JMenu("选择音乐");
        JMenu menu3=new JMenu("介绍");
        JMenu menu4=new JMenu("帮助");

        menu1.add(renew);
        menu1.add(last);
        menu1.add(next);
        menu1.add(choose);
        renew.addActionListener(this);
        last.addActionListener(this);
        next.addActionListener(this);
        choose.addActionListener(this);
        //第二个

        menu2.add(nor);
        menu2.add(item2_2);
        menu2.add(item2_3);
        menu2.add(item2_4);
        menu2.add(item2_5);
        menu2.add(item2_6);
        item2_2.addActionListener(this);
        item2_3.addActionListener(this);
        item2_4.addActionListener(this);
        item2_5.addActionListener(this);
        item2_6.addActionListener(this);

        menu3.add(item3_1);
        item3_1.addActionListener(this);
        menu4.add(about);
        about.addActionListener(this);
        //添加到菜单栏
        jmb.add(menu1);
        jmb.add(menu2);
        jmb.add(menu3);
        jmb.add(menu4);
        this.setJMenuBar(jmb);	//添加菜单栏，不能设定位置，会自动放在最上部
        /*
         * 如果先放大的图片再放下的会把小的给覆盖，不能看到
         * 图片有大小。把小的图片放在大的图片上面
         * 所以添加图片组件的时候有顺序，要注意把小的放在大的上面
         */
        //小图片

        //障碍的设计
        treeInit();

        //做笼子
        targetInit();

        //推箱子人物的初始化
        WolfInit();

        //羊的初始化
        sheepInit();

        //背景图片，大的
        //添加背景图片到窗体中
        backGroundInit();

        //设置整个窗体
        setForm();

        //注册监听
        this.addKeyListener(this);
    }


    //设置整个窗体
    public void setForm() {

        // TODO Auto-generated method stub
        this.setTitle("灰太狼的羊村收集之旅");
        this.setSize(825,645);
        //禁止用户改变窗体大小
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口居中显示
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    //按键反应
     public void actionPerformed(ActionEvent e){
        if(e.getSource()==renew){sound.mystop();setVisible(false); new Members();dispose();}
         if(e.getSource()==choose){System.exit(0);}

         if(e.getSource()==next){
             sound.mystop();
             setVisible(false);
            new Members2();
             dispose();
        }


         if(e.getSource()==about)
         {
             JOptionPane.showMessageDialog(null, "        灰太狼抓羊v3.0版\n     开发者：老衲掐指一算\n" +
                     "\n更多游戏项目请联系微信: 94250\n            联系也没有用");
         }

         if(e.getSource()==item3_1)
         {
             Icon i = new ImageIcon("pic/s.png");
             JOptionPane.showMessageDialog(null, "灰太狼老是被羊欺负，实在太可怜了，让我们来帮帮它吧！","老衲",2,i);
         }

         if(e.getSource()==nor)
         {}

         else if(e.getSource()==item2_2)
         {
             if(sound.isplay()){sound.mystop();}
             sound.setMusic("popo.mid");
             sound.isplay();
             sound.loadSound();
             nor.setEnabled(true);
             item2_2.setEnabled(false);
             item2_3.setEnabled(true);
             item2_4.setEnabled(true);
             item2_5.setEnabled(true);
         }
         else if(e.getSource()==item2_3)
         {
             if(sound.isplay()){sound.mystop();}
             sound.setMusic("guang.mid");
             sound.isplay();
             sound.loadSound();
             nor.setEnabled(true);
             item2_2.setEnabled(true);
             item2_3.setEnabled(false);
             item2_4.setEnabled(true);
             item2_5.setEnabled(true);

         }
         else if(e.getSource()==item2_4)
         {
             if(sound.isplay()){sound.mystop();}
             sound.setMusic("qin.mid");
             sound.isplay();
             sound.loadSound();
             nor.setEnabled(true);
             item2_2.setEnabled(true);
             item2_3.setEnabled(true);
             item2_4.setEnabled(false);
             item2_5.setEnabled(true);
         }
         else if(e.getSource()==item2_5)
         {
             if(sound.isplay()){sound.mystop();}
             sound.setMusic("nor.wav");
             sound.isplay();
             sound.loadSound();
             nor.setEnabled(true);
             item2_2.setEnabled(true);
             item2_3.setEnabled(true);
             item2_4.setEnabled(true);
             item2_5.setEnabled(false);
         }
         else if(e.getSource()==item2_6)
         {
             if(sound.isplay()){sound.mystop();}
         }

     }


    //背景图片初始化
    private void backGroundInit() {
        // TODO Auto-generated method stub
        Icon i = new ImageIcon("pic/floor.png");
        //使用JLabel制作背景
        JLabel lab_bg = new JLabel(i);
        //设置要添加的组件的位置与大小
        lab_bg.setBounds(0, 0, 800, 600);
        //将这个东西添加到窗体里面
        this.add(lab_bg);
    }


    //羊所在的位置初始化
    private void sheepInit() {
        // TODO Auto-generated method stub
        //三只羊
        Icon i = new ImageIcon("pic/7.png");

        JLabel jb1 = new JLabel(i);
        jb1.setBounds(6 * 50, 4 * 50, 50, 50);
        this.add(jb1);
        //羊所在位置的值设置为4
        datas[4][6] = 4;
        sheep[4][6] = jb1;


        JLabel jb2 = new JLabel(i);
        jb2.setBounds(6 * 50, 6 * 50, 50, 50);
        this.add(jb2);

        datas[6][6] = 4;
        sheep[6][6] = jb2;

        JLabel jb3 = new JLabel(i);
        jb3.setBounds(6 * 50, 10 * 50, 50, 50);
        this.add(jb3);

        datas[10][6] = 4;
        sheep[10][6] = jb3;
    }


    JLabel jb = null;
    private void WolfInit() {
        // TODO Auto-generated method stub
        //人物最初位置在哪里？
        wx = 4 ;
        wy = 5 ;

        //使用一张图片来模拟人物
        //1.创建一张图片，人物图片
        Icon i = new ImageIcon("pic/3.png");
        //2.使用JLabel组件模拟人物
        jb = new JLabel(i);
        //3.设置人物在屏幕上的显示位置
        //人物的显示位置放置在何处较为合理？----------------
        jb.setBounds(wx*50, wy*50, 50, 50);
        //4.把这个人物放到窗体里面
        this.add(jb);
    }


    //笼子的位置初始化
    private void targetInit() {
        // TODO Auto-generated method stub
        Icon i = new ImageIcon("pic/target.png");

        JLabel jb1 = new JLabel(i);
        jb1.setBounds(14 * 50, 10 * 50,50,50);
        this.add(jb1);
        datas[10][14] = 8;

        JLabel jb2 = new JLabel(i);
        jb2.setBounds(13 * 50, 10 * 50, 50, 50);
        this.add(jb2);
        datas[10][13] = 8;

        JLabel jb3 = new JLabel(i);
        jb3.setBounds(14 * 50, 9 * 50, 50, 50);
        this.add(jb3);
        datas[9][14] = 8;
    }


    //树木的初始化
    private void treeInit() {
        // TODO Auto-generated method stub

        Icon k = new ImageIcon("pic/tree.png");
        JLabel t = null;

        for(int i = 0;i < datas.length;i ++){

            for(int j = 0;j < datas[i].length;j ++){

                if(datas[i][j] == 1){
                    t = new JLabel(k);
                    t.setBounds(j*50, i*50, 50, 50);
                    this.add(t);
                }
            }
        }
    }

    //判断是否胜利
    private void victory()
    {
        if(num == total){
            //设计一个弹框，提示游戏完成
            /*Icon i = new ImageIcon("pic/6.png");
            JOptionPane.showMessageDialog(null, "灰太狼抓走了三只美羊羊","喜羊羊",2,i);
            /*
             * 如果要设置关卡，则要在这里添加信息
             * 注意修改num的值
             * 根据自己关卡的数量，把datas数组设计成三维的额
             */

            String msg="恭喜您通过第1关,抓住了三只美羊羊!!!\n是否要进入下一关？";
            int type=JOptionPane.YES_NO_OPTION;
            String title="过关";
            int choice=0;
            choice=JOptionPane.showConfirmDialog(null,msg,title,type);
            if(choice==1)System.exit(0);
            else if(choice==0)
            {
                sound.mystop();
                setVisible(false);
                new Members2();
                dispose();
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }


    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

        /*
         * datas数值的情况
         * 0	空地
         * 1	树木
         * 4	羊
         * 8	空笼子
         * 12	放羊的笼子
         * 结合这些数值去看下面的代码
         */

        /*
         * W 向上
         * D 向右
         * S 向下
         * A 向左
         * 注意一个盲区，这个问题考虑了好久，在Java坐标体系中，坐标轴是水平方向为x轴，竖直方向为y轴
         * 而在数组中先水平方向，后竖直方向，所以在datas数组中填写数值为先y后x
         */

        if(e.getKeyCode() == KeyEvent.VK_W||e.getKeyCode() == KeyEvent.VK_UP){

            /*
             * 每一次按键都要讨论下面这些情况
             * 1.浪	树木
             * 2.狼	羊	  树木
             * 3.狼	羊	 羊
             * 4.狼	羊	 放羊的笼子
             * 5.狼	放羊的笼子	树
             * 6.狼	放羊的笼子	羊
             * 7.狼	放羊的笼子 	放羊的笼子
             * 上面的这些情况都不做处理，因为不能移动
             * 8.狼	空地
             * 9.狼	空笼子
             * 10.狼	羊	空地
             * 11.狼	羊	空笼子
             * 12.狼	放羊的笼子	空地
             * 13.狼	放羊的笼子	空笼子
             * 这些情况需要有相应的变化，见代码
             */
            if(datas[wy-1][wx] == 1){
                return;
            }

            if(datas[wy-1][wx] == 4 && datas[wy-2][wx] == 1){
                return;
            }

            if(datas[wy-1][wx] == 4 && datas[wy-2][wx] == 4){
                return;
            }

            if(datas[wy-1][wx] == 4 && datas[wy-1][wx] == 12){
                return;
            }

            if(datas[wy-1][wx] == 12 && datas[wy-2][wx] == 1){
                return;
            }

            if(datas[wy-1][wx] == 12 && datas[wy-2][wx] == 4){
                return;
            }

            if(datas[wy-1][wx] == 12 && datas[wy-2][wx] == 12){
                return;
            }
            //狼 空地
            if(datas[wy-1][wx] == 0){
                wy -= 1;
                //坐标得到的不是int类型。注意强制类型转化
                int x = (int)jb.getLocation().getX();
                int y = (int)jb.getLocation().getY();
                jb.setLocation(x, y - 50);
                Icon i = new ImageIcon("pic/3.png");
                jb.setIcon(i);
                return;
            }
            //狼 空笼子
            if(datas[wy-1][wx] == 8){
                wy -= 1;
                //坐标得到的不是int类型。注意强制类型转化
                int x = (int)jb.getLocation().getX();
                int y = (int)jb.getLocation().getY();
                jb.setLocation(x, y - 50);
                Icon i = new ImageIcon("pic/3.png");
                jb.setIcon(i);
                return;
            }
            //狼 羊 空地
            if(datas[wy-1][wx] == 4 && datas[wy-2][wx] == 0){
                datas[wy-1][wx] = 0;
                datas[wy-2][wx] = 4;
            }
            //狼 羊 空笼子
            if(datas[wy-1][wx] == 4 && datas[wy-2][wx] == 8){
                datas[wy-1][wx] = 0;
                datas[wy-2][wx] = 12;
                num ++;
            }
            //狼 放羊的笼子 空地
            if(datas[wy-1][wx] == 12 && datas[wy-2][wx] == 0){
                datas[wy-1][wx] = 8;
                datas[wy-2][wx] = 4;
                num --;
            }
            //狼 放羊的笼子 空笼子
            if(datas[wy-1][wx] == 12 && datas[wy-2][wx] == 8){
                datas[wy-1][wx] = 8;
                datas[wy-2][wx] = 12;
            }

            //羊的位置 在狼的wy-1  移动一格 wy-2 相当于从wy-100像素
            sheep[wy-1][wx].setLocation(wx*50, wy*50-100);
            sheep[wy-2][wx] = sheep[wy-1][wx];
            sheep[wy-1][wx] = null;
            wy -= 1;
            //坐标得到的不是int类型。注意强制类型转化
            int x = (int)jb.getLocation().getX();
            int y = (int)jb.getLocation().getY();
            jb.setLocation(x, y - 50);
            Icon i = new ImageIcon("pic/3.png");
            jb.setIcon(i);
            victory();
            return;
        }
        else if(e.getKeyCode() == KeyEvent.VK_D||e.getKeyCode()==KeyEvent.VK_RIGHT){

            if(datas[wy][wx+1] == 1){
                return;
            }

            if(datas[wy][wx+1] == 4 && datas[wy][wx+2] == 1){
                return;
            }

            if(datas[wy][wx+1] == 4 && datas[wy][wx+2] == 4){
                return;
            }

            if(datas[wy][wx+1] == 4 && datas[wy][wx+2] == 12){
                return;
            }

            if(datas[wy][wx+1] == 12 && datas[wy][wx+2] == 1){
                return;
            }

            if(datas[wy][wx+1] == 12 && datas[wy][wx+2] == 4){
                return;
            }

            if(datas[wy][wx+1] == 12 && datas[wy][wx+2] == 12){
                return;
            }

            if(datas[wy][wx+1] == 0){
                wx += 1;
                //坐标得到的不是int类型。注意强制类型转化
                int x = (int)jb.getLocation().getX();
                int y = (int)jb.getLocation().getY();
                jb.setLocation(x + 50, y);
                Icon i = new ImageIcon("pic/3.png");
                jb.setIcon(i);
                return;
            }

            if(datas[wy][wx+1] == 8){
                wx += 1;
                //坐标得到的不是int类型。注意强制类型转化
                int x = (int)jb.getLocation().getX();
                int y = (int)jb.getLocation().getY();
                jb.setLocation(x + 50, y);
                Icon i = new ImageIcon("pic/3.png");
                jb.setIcon(i);
                return;
            }

            if(datas[wy][wx+1] == 4 && datas[wy][wx+2] == 0){
                datas[wy][wx+1] = 0;
                datas[wy][wx+2] = 4;
            }

            if(datas[wy][wx+1] == 4 && datas[wy][wx+2] == 8){
                datas[wy][wx+1] = 0;
                datas[wy][wx+2] = 12;
                num ++;
            }

            if(datas[wy][wx+1] == 12 && datas[wy][wx+2] == 0){
                datas[wy][wx+1] = 8;
                datas[wy][wx+2] = 4;
                num --;
            }

            if(datas[wy][wx+1] == 12 && datas[wy][wx+2] == 8){
                datas[wy][wx+1] = 8;
                datas[wy][wx+2] = 12;
            }

            sheep[wy][wx+1].setLocation(wx*50+100, wy*50);
            sheep[wy][wx+2] = sheep[wy][wx+1];
            sheep[wy][wx+1] = null;

            wx += 1;
            //坐标得到的不是int类型。注意强制类型转化
            int x = (int)jb.getLocation().getX();
            int y = (int)jb.getLocation().getY();
            jb.setLocation(x + 50, y);
            Icon i = new ImageIcon("pic/3.png");
            jb.setIcon(i);
            victory();
            return;
        }
        else if(e.getKeyCode() == KeyEvent.VK_S||e.getKeyCode()==KeyEvent.VK_DOWN){

            if(datas[wy+1][wx] == 1){
                return;
            }

            if(datas[wy+1][wx] == 4 && datas[wy+2][wx] == 1){
                return;
            }

            if(datas[wy+1][wx] == 4 && datas[wy+2][wx] == 4){
                return;
            }

            if(datas[wy+1][wx] == 4 && datas[wy+2][wx] == 12){
                return;
            }

            if(datas[wy+1][wx] == 12 && datas[wy+2][wx] == 1){
                return;
            }

            if(datas[wy+1][wx] == 12 && datas[wy+2][wx] == 4){
                return;
            }

            if(datas[wy+1][wx] == 12 && datas[wy+2][wx] == 12){
                return;
            }

            if(datas[wy+1][wx] == 0){
                wy += 1;
                //坐标得到的不是int类型。注意强制类型转化
                int x = (int)jb.getLocation().getX();
                int y = (int)jb.getLocation().getY();
                jb.setLocation(x, y + 50);
                Icon i = new ImageIcon("pic/3.png");
                jb.setIcon(i);
                return;
            }

            if(datas[wy+1][wx] == 8){
                wy += 1;
                //坐标得到的不是int类型。注意强制类型转化
                int x = (int)jb.getLocation().getX();
                int y = (int)jb.getLocation().getY();
                jb.setLocation(x, y + 50);
                Icon i = new ImageIcon("pic/3.png");
                jb.setIcon(i);
                return;
            }

            if(datas[wy+1][wx] == 4 && datas[wy+2][wx] == 0){
                datas[wy+1][wx] = 0;
                datas[wy+2][wx] = 4;
            }

            if(datas[wy+1][wx] == 4 && datas[wy+2][wx] == 8){
                datas[wy+1][wx] = 0;
                datas[wy+2][wx] = 12;
                num ++;
            }

            if(datas[wy+1][wx] == 12 && datas[wy+2][wx] == 0){
                datas[wy+1][wx] = 8;
                datas[wy+2][wx] = 4;
                num --;
            }

            if(datas[wy+1][wx] == 12 && datas[wy+2][wx] == 8){
                datas[wy+1][wx] = 8;
                datas[wy+2][wx] = 12;
            }

            sheep[wy+1][wx].setLocation(wx*50, wy*50+100);
            sheep[wy+2][wx] = sheep[wy+1][wx];
            sheep[wy+1][wx] = null;

            wy += 1;
            //坐标得到的不是int类型。注意强制类型转化
            int x = (int)jb.getLocation().getX();
            int y = (int)jb.getLocation().getY();
            jb.setLocation(x, y + 50);
            Icon i = new ImageIcon("pic/3.png");
            jb.setIcon(i);
            victory();
            return;
        }
        else if(e.getKeyCode() == KeyEvent.VK_A||e.getKeyCode() == KeyEvent.VK_LEFT){

            if(datas[wy][wx-1] == 1){
                return;
            }

            if(datas[wy][wx-1] == 4 && datas[wy][wx-2] == 1){
                return;
            }

            if(datas[wy][wx-1] == 4 && datas[wy][wx-2] == 4){
                return;
            }

            if(datas[wy][wx-1] == 4 && datas[wy][wx-2] == 12){
                return;
            }

            if(datas[wy][wx-1] == 12 && datas[wy][wx-2] == 1){
                return;
            }

            if(datas[wy][wx-1] == 12 && datas[wy][wx-2] == 4){
                return;
            }

            if(datas[wy][wx-1] == 12 && datas[wy][wx-2] == 12){
                return;
            }

            if(datas[wy][wx-1] == 0){
                wx -= 1;
                //坐标得到的不是int类型。注意强制类型转化
                int x = (int)jb.getLocation().getX();
                int y = (int)jb.getLocation().getY();
                jb.setLocation(x - 50, y);
                Icon i = new ImageIcon("pic/A.png");
                jb.setIcon(i);
                return;
            }

            if(datas[wy][wx-1] == 8){
                wx -= 1;
                //坐标得到的不是int类型。注意强制类型转化
                int x = (int)jb.getLocation().getX();
                int y = (int)jb.getLocation().getY();
                jb.setLocation(x - 50, y);
                Icon i = new ImageIcon("pic/A.png");
                jb.setIcon(i);
                return;
            }

            if(datas[wy][wx-1] == 4 && datas[wy][wx-2] == 0){
                datas[wy][wx-1] = 0;
                datas[wy][wx-2] = 4;
            }

            if(datas[wy][wx-1] == 4 && datas[wy][wx-2] == 8){
                datas[wy][wx-1] = 0;
                datas[wy][wx-2] = 12;
                num ++;
            }

            if(datas[wy][wx-1] == 12 && datas[wy][wx-2] == 0){
                datas[wy][wx-1] = 8;
                datas[wy][wx-2] = 4;
                num --;
            }

            if(datas[wy][wx-1] == 12 && datas[wy][wx-2] == 8){
                datas[wy][wx-1] = 8;
                datas[wy][wx-2] = 12;
            }

            sheep[wy][wx-1].setLocation(wx*50-100, wy*50);
            sheep[wy][wx-2] = sheep[wy][wx-1];
            sheep[wy][wx-1] = null;

            wx -= 1;
            //坐标得到的不是int类型。注意强制类型转化
            int x = (int)jb.getLocation().getX();
            int y = (int)jb.getLocation().getY();
            jb.setLocation(x - 50, y);
            Icon i = new ImageIcon("pic/A.png");
            jb.setIcon(i);
            victory();
            return;
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

 }



//class Sound{
     // String path=new String("musics\\");

     // String  file=new String("popo.mid");
     // Sequence seq;
     //Sequencer midi;
     // boolean sign;
//void loadSound()
// {
// try {
//  seq=MidiSystem.getSequence(new File(path+file));
// midi=MidiSystem.getSequencer();
//   midi.open();
//  midi.setSequence(seq);
//  midi.start();
//   midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
//  }
// catch (Exception ex) {ex.printStackTrace();}
//  sign=true;
     // }
     //void mystop(){midi.stop();midi.close();sign=false;}
     //  boolean isplay(){return sign;}
// void setMusic(String e){file=e;}
//}


class Sound extends JFrame {
    String path=new String("musics\\");
    String  file=new String("nor.wav");
    File f;
    URI uri;
    URL url;
    AudioClip aau;
    boolean sign;
    void loadSound() {
        try {
            f = new File(path+file);
            uri=f.toURI();
            url=uri.toURL();
            aau = Applet.newAudioClip(url);
            aau.play();
        } catch (Exception e) { e.printStackTrace(); }
        sign=true;
    }
    void mystop(){aau.stop();sign=false;}
    boolean isplay(){return sign;}
    void setMusic(String e){file=e;}
}
