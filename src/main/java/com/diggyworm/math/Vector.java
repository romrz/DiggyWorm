/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package com.diggyworm.math;

public class Vector
{
	public float x;
	public float y;
	
	public Vector()
	{
		this(0,0);
	}

	public Vector(Vector vector)
	{
		this(vector.x, vector.y);
	}
	
	public Vector(int x, int y)
	{
		this((float) x, (float) y);
	}
	
	public Vector(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
}
