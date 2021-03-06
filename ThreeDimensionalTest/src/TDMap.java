import java.io.*;
import java.util.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*;
import com.jogamp.opengl.util.FPSAnimator;

public class TDMap {
    private List<List<Integer>> buildData = new ArrayList<List<Integer>>();
    public TDMap(String mapFile){
        Scanner ms;
        File mf = new File(mapFile);
        try{
            ms = new Scanner(mf);
        }catch(FileNotFoundException e){
            System.err.println("Error, file " + mapFile + " not found.");
            return;
        }
        while(ms.hasNextLine()) {
            String mapFileLine = ms.nextLine();
            List<Integer> mapLineIntegers = new ArrayList<Integer>();
            Scanner mfls = new Scanner(mapFileLine);
            while(mfls.hasNextInt()) {
                mapLineIntegers.add(mfls.nextInt());
            }
            buildData.add(mapLineIntegers);
        }
    }
    public boolean collision(float x1, float y1, float z1, float w1, float h1, float d1,
                             float x2, float y2, float z2, float w2, float h2, float d2){
        if(x1+w1<=x2 || x2+w2<=x1 || y1+h1<=y2 || y2+h2<=y1 || z1+d1<=z2 || z2+d2<=z1){
            return false;
        }else{
            return true;}
        }
    public boolean collision(float x, float y, float z, float width, float height, float depth){
        for(int i = 0; i < buildData.size(); i++){
           List<Integer> list = buildData.get(i);
           for(int j = 0; j < list.size(); j++){
                int value = list.get(j);
                if(value == 1){
                    if( collision(x,y,z,width,height,depth,
                                     j,0.0f,i,1,1,1)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean cubeHere(float x, float z){
        if(x < 0 || z < 0 || z > buildData.size()-1 || x > buildData.get(0).size()-1){
            return false;
        }
        int row = (int)Math.floor(z);
        int col = (int)Math.floor(x);
         return buildData.get(row).get(col) == 1;
    }
    public void draw(GL2 gl, float cx, float cy, float cz, float cDirection){
        for(int i = 0; i < buildData.size(); i++){
            List<Integer> list = buildData.get(i);
            for(int j = 0; j < list.size(); j++){
                int value = list.get(j);
                if(value == 1){
                    if (ThreeDimensionalTest.topView) {
                        drawCube(gl,j-cx,-20,i-cz,-cDirection);
                    } else {
                        drawCube(gl,j-cx,-cy,i-cz,-cDirection);
                    }
                }
            }
        }
    }
    private void drawCube(GL2 gl, float x, float y, float z, float direction){
        gl.glLoadIdentity();
        if (ThreeDimensionalTest.topView) {
            gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        } else {
            gl.glRotatef(direction,0.0f,1.0f,0.0f);
        }
        if (ThreeDimensionalTest.topView) {
            gl.glTranslatef(x,y,z);
        } else {
            gl.glTranslatef(x,y,z);
        }
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0.0f,0.0f,1.0f);
        gl.glColor3f(1.0f, 0.5f, 0.0f);
        gl.glTexCoord2d(0.0f, 0.0f);
        gl.glVertex3f(0.0f,1.0f,1.0f);
        gl.glTexCoord2d(1.0f, 0.0f);
        gl.glVertex3f(1.0f,1.0f,1.0f);
        gl.glTexCoord2d(1.0f, 1.0f);
        gl.glVertex3f(1.0f,0.0f,1.0f);
        gl.glTexCoord2d(0.0f, 1.0f);
        gl.glVertex3f(0.0f,0.0f,1.0f);

        gl.glNormal3f(-1.0f,0.0f,0.0f);
        gl.glColor3f(0.5f,0.25f,0.0f);
        gl.glTexCoord2d(0.0f, 0.0f);
        gl.glVertex3f(0.0f,1.0f,1.0f);
        gl.glTexCoord2d(1.0f, 0.0f);
        gl.glVertex3f(0.0f,1.0f,0.0f);
        gl.glTexCoord2d(1.0f, 1.0f);
        gl.glVertex3f(0.0f,0.0f,0.0f);
        gl.glTexCoord2d(0.0f, 1.0f);
        gl.glVertex3f(0.0f,0.0f,1.0f);

        gl.glNormal3f(1.0f,0.0f,0.0f);
        gl.glColor3f(0.5f,0.25f,0.0f);
        gl.glTexCoord2d(0.0f, 0.0f);
        gl.glVertex3f(1.0f,1.0f,1.0f);
        gl.glTexCoord2d(1.0f, 0.0f);
        gl.glVertex3f(1.0f,1.0f,0.0f);
        gl.glTexCoord2d(1.0f, 1.0f);
        gl.glVertex3f(1.0f,0.0f,0.0f);
        gl.glTexCoord2d(0.0f, 1.0f);
        gl.glVertex3f(1.0f,0.0f,1.0f);

        gl.glNormal3f(0.0f,1.0f,0.0f);
        gl.glColor3f(0.75f,0.375f,0.0f);
        gl.glTexCoord2d(0.0f, 0.0f);
        gl.glVertex3f(0.0f,1.0f,1.0f);
        gl.glTexCoord2d(1.0f, 0.0f);
        gl.glVertex3f(0.0f,1.0f,0.0f);
        gl.glTexCoord2d(1.0f, 1.0f);
        gl.glVertex3f(1.0f,1.0f,0.0f);
        gl.glTexCoord2d(0.0f, 1.0f);
        gl.glVertex3f(1.0f,1.0f,1.0f);

        gl.glNormal3f(0.0f,-1.0f,0.0f);
        gl.glColor3f(0.75f,0.375f,0.0f);
        gl.glTexCoord2d(0.0f, 0.0f);
        gl.glVertex3f(0.0f,0.0f,1.0f);
        gl.glTexCoord2d(1.0f, 0.0f);
        gl.glVertex3f(0.0f,0.0f,0.0f);
        gl.glTexCoord2d(1.0f, 1.0f);
        gl.glVertex3f(1.0f,0.0f,0.0f);
        gl.glTexCoord2d(0.0f, 1.0f);
        gl.glVertex3f(1.0f,0.0f,1.0f);

        gl.glNormal3f(0.0f,0.0f,-1.0f);
        gl.glColor3f(1.0f, 0.5f, 0.0f);
        gl.glTexCoord2d(0.0f, 0.0f);
        gl.glVertex3f(0.0f,1.0f,0.0f);
        gl.glTexCoord2d(1.0f, 0.0f);
        gl.glVertex3f(1.0f,1.0f,0.0f);
        gl.glTexCoord2d(1.0f, 1.0f);
        gl.glVertex3f(1.0f,0.0f,0.0f);
        gl.glTexCoord2d(0.0f, 1.0f);
        gl.glVertex3f(0.0f,0.0f,0.0f);
        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);

    }
}
