package stepFinal;

import processing.core.PApplet;

public class Target {

    float x, y, r;
    boolean exploded, failed;

    Target(float x, float y, float r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    void display(PApplet p5){
        p5.pushStyle();
        p5.stroke(255);
        if(this.exploded){
            p5.fill(255, 0, 0);
        }
        else if(this.failed){
            p5.fill(0, 0, 255);
        }
        else {
            p5.fill(0);
        }

        p5.circle(this.x, this.y, 2*this.r);
        p5.popStyle();
    }

    void update(PApplet p5){
        if(this.exploded && this.y < p5.height + 2*this.r){
            this.y += 5;
        }
        else if(this.failed && this.y > -2*this.r){
            this.y -= 5;
        }
    }

    boolean isExploded(PApplet p5, Projectil p){
        return (p5.dist(this.x, this.y, p.x, p.y) < p5.max(p.r,this.r));
    }
}
