package stepFinal;

import processing.core.PApplet;

public class Target {

    float x, y, r;

    enum ESTAT {PENDENT, EXPLOTAT, FALLAT};
    ESTAT estat;

    Target(float x, float y, float r){
        this.x = x;
        this.y = y;
        this.r = r;
        this.estat = ESTAT.PENDENT;
    }

    void setEstat(ESTAT e){
        this.estat = e;
    }

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

    void update(PApplet p5){
        if(this.estat == ESTAT.EXPLOTAT && this.y < p5.height + 2*this.r){
            this.y += 5;
        }
        else if(this.estat == ESTAT.FALLAT && this.y > -2*this.r){
            this.y -= 5;
        }
    }

    boolean esImpactatPer(PApplet p5, Projectil p){
        return (p5.dist(this.x, this.y, p.x, p.y) < p5.max(p.r,this.r));
    }
}
