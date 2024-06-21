package stepFinal;

import processing.core.PApplet;
import processing.core.PFont;
import processing.sound.SoundFile;

public class TirParabolic extends PApplet {

    // Array d'objectes de classe Target
    Target[] targets;

    // Declaram 1 variable de classe Projectil
    Projectil p;

    // Força i altura del canó
    float f = 100;
    float h = 400;

    // Dispar del projectil fet o no
    boolean disparat = false;

    // Temps
    float t =0;

    // Gravetat
    float g = 9.8f;

    // Estadístiques del joc
    int numShots = 0, numPoints = 0, numTargets = 0;

    // Sons d'explosió i d'impacte
    SoundFile soCano, soImpacte;

    // Fonts del texte
    PFont font1, font2;

    public static void main(String[] args) {
        PApplet.main("stepFinal.TirParabolic");
    }

    public void settings(){
        size(1600, 800);
    }

    public void setup(){
        // Crea el projectil amb el constructor
        p = new Projectil(100, h, 50);
        p.setImatgeCano(this);

        // Crea els targets
        setTargets(3, 9);

        // Carrega els sons
        soCano = new SoundFile(this, "explosio.wav");
        soImpacte = new SoundFile(this, "impacte.wav");

        // Carrega les fonts
        font1 = createFont("EvilEmpire.otf", 34);
        font2 = createFont("Sono.ttf", 14);

    }

    public void draw(){
        background(220);

        // Dibuixa i actualitza la posició dels targets
        for(int i=0; i<targets.length; i++){
            targets[i].display(this);
            targets[i].update(this);
        }

        // Dibuixa el projectil
        p.display(this);

        // Dibuixa les estadístiques del joc
        displayInfo();

        // Si no s'ha disparat, configura posició H, força F i direcció A del canó
        if (!disparat) {
            float a = map(mouseY, this.h-100, this.h+100, 0, -PI);
            p.setProperties(a, mouseX, mouseY, f, h);
        }
        // Si s'ha disparat i el projectil està dins del camp de joc (pantalla)
        else if (disparat && p.x <= width && p.y <= height) {

            // Actualitzam la posició del projectil
            p.update(t, g);
            // Actualitzam el temps
            t += 0.1;

            // Comprovam si el projectil col·lisiona amb els objectius
            for(int i=0; i<targets.length; i++){
                if(targets[i].estat != Target.ESTAT.EXPLOTAT && targets[i].esImpactatPer(this, p)){
                    targets[i].setEstat(Target.ESTAT.EXPLOTAT);
                    numPoints++;
                    soImpacte.play();
                }
            }
        }
        // Si s'ha disparat i el projectil ha sortir del camp de joc (pantalla)
        else if (disparat && (p.x > width || p.y > height)) {

            // Posam la resta d'objectius a fallats (failed)
            for(int i=0; i<targets.length; i++){
                if(targets[i].estat == Target.ESTAT.PENDENT){
                    targets[i].setEstat(Target.ESTAT.FALLAT);
                }
            }
            // Misatge per resetear la posició dels nous objectius
            textAlign(CENTER); textSize(36); fill(255, 0, 0);
            text("Press R key to set up the next scenario", width/2, height/2);
        }


    }

    // Mostra estadístiques i instruccions
    void displayInfo(){

        // Títol del joc
        fill(0); textAlign(LEFT); textSize(34);
        textFont(font1);
        text("Tir Parabòlic", 50, 50);

        // Marcador
        fill(0); textAlign(RIGHT);
        text("Score", width - 50, 50);

        // Estadístiques
        textSize(14);
        textFont(font2);
        String percentatge = nf(100*(numPoints/(float)numTargets), 2, 2);
        text("Rate: "+ percentatge+"%", width - 50, 80);
        text("Hits: "+ numPoints + " / " + numTargets, width - 50, 100);
        text("Shots: "+ numShots, width - 50, 120);

        // Instruccions
        fill(0); textSize(14); textAlign(LEFT);
        textFont(font2);
        text("Press S key to shot your cannon.", 50, height-90);
        text("Use MOUSE to set your cannon direction.", 50, height-70);
        text("Press ARROW KEYS to set up your cannon.", 50, height-50);
        text("UP: move up, DOWN: move down, LEFT: decrease force, RIGHT: increase force.", 50, height-30);
    }

    // Estableix un número aleatori de targets
    void setTargets(int n1, int n2){

        // Crea un número aleatori (entre n1 i n2) d'objectius
        int nt = (int) random(n1, n2);
        targets = new Target[nt];

        // Incrementa el número de targets
        numTargets += nt;

        // Posiciona els objectius en el terreny de joc
        for(int i=0; i<nt; i++){
            float x = random(width/2, width);
            float y = random(height/5, 4*height/5);
            float r = random(20, 60);
            targets[i] = new Target(x, y, r);
        }
    }

    public void keyPressed() {

        // Dispara el canó
        if (key == 's' || key=='S') {
            if(!disparat){
                numShots++;
                soCano.play();
            }
            disparat = true;
        }
        // Reseteja la posició dels objectius
        else if (key == 'r') {
            disparat = false;
            t = 0;
            p.update(t, g);
            setTargets(3, 12);
        }
        // Augmenta la força del dispar
        else if (keyCode == RIGHT) {
            f += 10;
            f = constrain(f, 10, 300);
        }
        // Redueix la força del dispar
        else if (keyCode == LEFT) {
            f -= 10;
            f = constrain(f, 10, 300);
        }
        // Augmenta l'altura H del canó
        else if (keyCode == UP) {
            h -= 10;
        }
        // Redueix l'altura H del canó
        else if (keyCode == DOWN) {
            h += 10;
        }
    }

}
