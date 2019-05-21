import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * It represent a traffic light.
 * @author Arie and Gad.
 */
class Ramzor 
{
	int numOfLights;
	int xP[], yP[];
	int diameter;
	Color colorLight[];

	Ramzor(int num, int dia, int x0, int y0)
	{
		this.numOfLights = num;
		this.diameter = dia;
		this.xP = new int[numOfLights];
		this.yP = new int[numOfLights];
		this.colorLight = new Color[numOfLights];
		this.colorLight[0] = Color.LIGHT_GRAY;
		this.xP[0] = x0; this.yP[0] = y0;
	}

	Ramzor(int num, int dia, int x0, int y0, int x1, int y1)
	{
		this.numOfLights = num;
		this.diameter = dia;
		this.xP = new int[numOfLights];
		this.yP = new int[numOfLights];
		this.colorLight = new Color[numOfLights];
		this.colorLight[0] = Color.RED;
		this.colorLight[1] = Color.GRAY;
		this.xP[0] = x0; this.yP[0] = y0;
		this.xP[1] = x1; this.yP[1] = y1;
	}

	Ramzor(int num, int dia, int x0, int y0, int x1, int y1, int x2, int y2)
	{
		this.numOfLights = num;
		this.diameter = dia;
		this.xP = new int[numOfLights];
		this.yP = new int[numOfLights];
		this.colorLight = new Color[numOfLights];
		this.colorLight[0] = Color.RED;
		this.colorLight[1] = Color.LIGHT_GRAY;
		this.colorLight[2] = Color.LIGHT_GRAY;
		this.xP[0] = x0; this.yP[0] = y0;
		this.xP[1] = x1; this.yP[1] = y1;
		this.xP[2] = x2; this.yP[2] = y2;
	}

	void draw(Graphics page)
	{
		for(int i = 0; i < numOfLights; i++) {
			page.setColor(colorLight[i]);
			page.fillOval(xP[i], yP[i], diameter, diameter);
		}
	}
}
