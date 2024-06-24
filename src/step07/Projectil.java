package step07;

import processing.core.PApplet;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class Projectil {

    // Posició del canó
    float x0, y0;

    // Posició del projectil
    float x, y;

    // força i angle del dispar
    float f, a;

    // Vector velocitat inicial
    float v0x, v0y, vy;

    // Radi del projectil
    float r=10;

    Projectil(float x, float h, float f){

        // Posició del canó
        this.x0 = x;
        this.y0 = h;

        // Posició inicial del projectil
        this.x = x;
        this.y = h;

        // Força i angel del dispar
        this.f = f;
        this.a = 0;

        // Velocitats X,Y del dispar
        this.v0x = + f * cos(this.a);
        this.v0y = - f * sin(this.a);
        this.vy = this.v0y;
    }

    // Setter de les propietats
    void setProperties(float a, float f, float h){

        // Posició y inicial (canó i projectil)
        this.y = h;
        this.y0 = h;

        // Angle i força del dispar
        this.a = a;
        this.f = f;

        // Velocitats X,Y del dispar
        this.v0x = + this.f * cos(this.a);
        this.v0y = - this.f * sin(this.a);
        this.vy = this.v0y;
    }

    //Dibuixa el canó i el projectil
    void display(PApplet p5){

        float dx = this.x0 + this.f *cos(this.a);
        float dy = this.y0 - this.f *sin(this.a);

        p5.pushStyle();

        // Tub del canó
        p5.strokeWeight(10); p5.stroke(0);
        p5.line(this.x0, this.y0, dx, dy);

        // Cos del canó
        p5.stroke(0); p5.fill(0);
        p5.rect(this.x0 - 20, this.y0, 40, 20);

        // Projectil
        p5.noStroke(); p5.fill(255, 0, 0);
        p5.circle(this.x, this.y, 2*this.r);

        // Paràmetres del projectil (Posició, Velocitat i Força)
        p5.fill(0); p5.textSize(14); p5.textAlign(p5.LEFT);
        p5.text("Sx: " + p5.nf(this.x, 3, 2)+", Sy: " + p5.nf(this.y, 3, 2), 55, 80);
        p5.text("Vx: " + p5.nf(this.v0x, 2, 2) + ",Vy: " + p5.nf(this.vy, 2, 2), 55, 100);
        p5.text("F: " + p5.nf(this.f, 2, 2), 55, 120);

        p5.popStyle();

    }

    void update(float t, float g){

        this.x = this.x0 + this.v0x * t;
        this.y = this.y0 + this.vy*t - 0.5f*g*t*t;

        this.vy = this.v0y + g*t;
    }
}
