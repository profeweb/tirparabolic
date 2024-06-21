package stepFinal;

import processing.core.PApplet;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class Projectil {

    float x0, x, y;
    float h, v, a;
    float v0x, v0y, vy;
    float r;

    Projectil(float x, float h, float v){
        this.x0 = x;
        this.x = x;
        this.y = h;
        this.h = h;
        this.v = v;
        this.a = 0;

        this.v0x = v * cos(this.a);
        this.v0y = - v * sin(this.a);
        this.vy = this.v0y;
    }

    void setProperties(float a, float mx, float my, float f, float h){

        this.y = h;
        this.h = h;

        this.a = a; //map(mouseY, this.h-100, this.h+100, 0, -PI);
        this.v = f;

        this.v0x = + this.v * cos(this.a);
        this.v0y = - this.v * sin(this.a);
        this.vy = this.v0y;
    }

    void update(float t, float g){

        this.x = this.x0 + this.v0x * t;
        this.y = this.h + this.vy*t - 0.5f*g*t*t;

        this.vy = this.v0y + g*t;
    }

    void display(PApplet p5){

        float dx = this.x0 + this.v*cos(this.a);
        float dy = this.h - this.v*sin(this.a);

        p5.pushStyle();
        p5.strokeWeight(10); p5.stroke(0);
        p5.line(this.x0, this.h, dx, dy);
        p5.stroke(0); p5.fill(0);
        p5.rect(this.x0 - 20, this.h, 40, 20);

        p5.noStroke(); p5.fill(0);
        p5.circle(this.x, this.y, 10);

        p5.fill(0); p5.textSize(14); p5.textAlign(p5.LEFT);
        p5.text("Sx: " + p5.nf(this.x, 3, 2)+", Sy: " + p5.nf(this.y, 3, 2), 55, 80);
        p5.text("Vx: " + p5.nf(this.v0x, 2, 2) + ",Vy: " + p5.nf(this.vy, 2, 2), 55, 100);
        p5.text("F: " + p5.nf(this.v, 2, 2), 55, 120);

        p5.popStyle();

    }
}
