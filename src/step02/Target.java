package step02;

import processing.core.PApplet;

public class Target {

    // Posició (x,y) i radi
    float x, y, r;

    // Estat del target
    enum ESTAT {PENDENT, EXPLOTAT, FALLAT};
    ESTAT estat;

    // Constructor
    Target(float x, float y, float r){
        this.x = x;
        this.y = y;
        this.r = r;
        this.estat = ESTAT.PENDENT;
    }

    // Setter de la propietat estat
    void setEstat(ESTAT e){
        this.estat = e;
    }

    // Dibuixa el target
    void display(PApplet p5){
        p5.pushStyle();
        p5.noStroke();
        if(estat == ESTAT.EXPLOTAT){
            p5.fill(255, 0, 0);
        }
        else if(estat == ESTAT.FALLAT){
            p5.fill(0, 0, 255);
        }
        else {
            p5.fill(0);
        }
        p5.circle(this.x, this.y, 2*this.r);
        p5.popStyle();
    }

}
