package com.common;
/*import java.util.ArrayList;
import java.util.List;

*//**
 * @Author xcd
 * @Aata 2019年1月11日
 * @Description
 *//*
public class Test {
	static class Stack{}
	public static void main(String[] args) {
		List<byte[]> list = new ArrayList<>();
		int count = 1;
		while (true) {
			list.add(new byte[5*1024*1024]);
			String  s = "a";
			count++;
			System.out.println(count);
		}
	}
}*/
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Test extends Applet {
    private static final long serialVersionUID = -1616773050265562622L;

    Image image;
    Graphics graphics;
    int width = 568;
    int height = 350;

    @Override
    public void init() {
        setBackground(Color.black);
        this.setSize(width, height);
        image = createImage(width, height);
        graphics = image.getGraphics();
    }

    @Override
    public void paint(Graphics g) {
        // 用红色来填充清除指定的矩形。
        graphics.clearRect(0, 0, width, height);
        graphics.setColor(Color.red);
        double x, y, r;
        // 笛卡儿数学公式: (x*x+y*y-2ax)2=4a*a(x*x+y*y)
        for (int i = 0; i <= 90; i++)
            for (int j = 0; j <= 90; j++) {
                r = Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j)) * 18;
                x = r * Math.cos(Math.PI / 45 * j) * Math.sin(Math.PI / 45 * i) + width / 2;
                y = -r * Math.sin(Math.PI / 45 * j) + height / 4;
                graphics.fillOval((int) x, (int) y, 2, 2);
            }
        g.drawImage(image, 0, 0, this);
    }
}
