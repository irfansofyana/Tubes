from OpenGL.GL import *
from OpenGL.GLUT import *
from OpenGL.GLU import *
from transformation2D import *
from transformation3D import *
from random import *
import sys
import math
import threading

#declaration of neededs variables
Wireframe = True
width = 700
height = 700
x_rot = 0
y_rot = 0
angle = 0
lx = 0
lz = -1
xx = 0
zz = 5
yy = 0
ly = 1

#define class point. Point has 3 components (x,y,z)
class Point:
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z
        self.arrPoint = []

    def add_point(self, x, y, z):
        temp = Point(float(x), float(y), float(z))
        self.arrPoint.append(temp)

#initialize variables for point
initial = Point(0,0,0)
titik = Point(0,0,0)

#2D was selected (calling all transformation and method for 2D)
def run2d():
    print("Masukan banyak titik: ")
    n = int(input())
    print("Masukan titik-titik tersebut:")
    for i in range(0, n):
        x,y = input().split()
        titik.add_point(x,y,0)
        initial.add_point(x,y,0)
    makeWindow2D()

#Draw Shape of the Blocks (So every changes be made, call this method)
def drawShape2D(x):
    glBegin(GL_POLYGON)
    for i in x.arrPoint:
        glColor3f(random(), random(), random())
        glVertex2f(i.x, i.y)
    glEnd()

#Draw Axis for 2D 
def drawAxis():
    glBegin(GL_LINES)

    glColor3f(random(), random(), random())
    glVertex2f(0,width)
    glVertex2f(0, -width)

    glColor3f(random(), random(), random())
    glVertex2f(height,0)
    glVertex2f(-height,0)

    glEnd()

#Draw all the parts for the window 2D
def draw2D():
    while (True):
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) # clear the screen
        glLoadIdentity()
        refresh2d(width, height)
        drawAxis()
        drawShape2D(titik)
        glutSwapBuffers()
        comm = input()
        if (comm == "exit"):
            sys.exit()
        elif (comm != "multiple"):
            doCommand2D(comm)
        elif (comm == "multiple"):
            tmp = input()
            n = int(tmp)
            for i in range(0, n):
                comm = input()
                doCommand2D(comm)

#do the command for 2d only
def doCommand2D(comm):
    if (comm == "translate"):
        x, y = input().split()
        P = translate(titik, float(x), float(y))
    elif (comm == "dilate"):
        x = input()
        P = dilate(titik, float(x))
    elif (comm == "reflect"):
        tmp = input()
        if (len(tmp) == 1):
            if (tmp == "x"):
                #reflect to x axis
                P = reflect(titik, 0, 0, 0)
            else:
                #reflect to y axis
                P = reflect(titik, 1, 0, 0)
        else:
            if (tmp[0]=='y'):
                if (len(tmp) == 3):
                    #reflect to y=x
                    P = reflect(titik, 2, 0, 0)
                else:
                    #reflect to y=-x
                    P = reflect(titik, 3, 0, 0)

            else:
                #reflect to point(a, b)
                a,b = tmp.split()
                P = reflect(titik, 4, float(a), float(b))
    elif (comm == "shear"):
        a, k = input().split()
        P = shear(titik, a, float(k))
    elif (comm == "rotate"):
        deg, a, b = input().split()
        P = rotate(titik, float(deg), float(a), float(b))
    elif (comm == "stretch"):
        param, x = input().split()
        P = stretch(titik, param, float(x))
    elif (comm == "custom"):
        a,b,c,d = input().split()
        P = custom(titik, float(a), float(b), float(c), float(d))
    elif (comm == "reset"):
        P = reset(titik, initial)


#Make the window of the program 2D
def makeWindow2D():
    glutInit()                                             # initialize glut
    glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_ALPHA | GLUT_DEPTH)
    glutInitWindowSize(width, height)                      # set window size
    glutInitWindowPosition(0, 0)                           # set window position
    window = glutCreateWindow("Tubes Algeo 2D")              # create window with title
    glutDisplayFunc(draw2D)                                  # set draw function callback
    glutIdleFunc(draw2D)                                     # draw all the time
    glutMainLoop()

#Buffer the drawing for 2D
def refresh2d(width, height):
    glViewport(0,0 , width, height)
    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    glOrtho(-width, width, -height, height, 0.0, 1.0)
    glMatrixMode (GL_MODELVIEW)
    glLoadIdentity()

#3D was selected (calling all transformation and method for 3D)
def run3d():
    tmpArr = [(-0.5, -0.5, 0.5),
    (0.5, -0.5, 0.5), (0.5, 0.5, 0.5), (-0.5, 0.5, 0.5),
    (-0.5, -0.5, -0.5),(-0.5, 0.5, -0.5), ( 0.5, 0.5, -0.5),( 0.5, -0.5, -0.5), (-0.5, -0.5, 0.5)
    ,(-0.5, 0.5, 0.5), (-0.5, 0.5, -0.5), (-0.5, -0.5, -0.5), ( 0.5, -0.5, -0.5), ( 0.5, 0.5, -0.5),
    ( 0.5, 0.5, 0.5), ( 0.5, -0.5, 0.5), (-0.5, 0.5, 0.5), ( 0.5, 0.5, 0.5), ( 0.5, 0.5, -0.5),
    (-0.5, 0.5, -0.5), (-0.5, -0.5, 0.5), (-0.5, -0.5, -0.5),( 0.5, -0.5, -0.5),( 0.5, -0.5, 0.5)]
    for i in tmpArr:
        titik.add_point(i[0], i[1], i[2])
        initial.add_point(i[0], i[1], i[2])
    makeWindow3D()
    return

#Make the window of the program 3D
def makeWindow3D():
    global window
    glutInit()                                             # initialize glut
    glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_ALPHA | GLUT_DEPTH)
    glutInitWindowSize(width, height)                      # set window size
    glutInitWindowPosition(0, 0)
    window = glutCreateWindow("Tubes Algeo 3D")
    glutDisplayFunc(draw3D)
    glutIdleFunc(draw3D)
    glutReshapeFunc(ReSizeGLScene)
    InitGL(width, height)
    glutMainLoop()

#draw the axis for 3D
def drawAxis3D():
    glBegin(GL_LINES)

    glColor3f(1,0,0)
    glVertex3f(0,500,0)
    glVertex3f(0, -500,0)

    glColor3f(0,0,1)
    glVertex3f(500,0,0)
    glVertex3f(-500,0,0)

    glColor3f(0,1,0)
    glVertex3f(0,0,500)
    glVertex3f(0,0,-500)

    glEnd()

#initialize openGl window
def InitGL(Width, Height):
    glClearColor(0.0, 0.0, 0.0, 0.0)
    glClearDepth(1.0)
    glDepthFunc(GL_LESS)
    glEnable(GL_DEPTH_TEST)
    glPolygonMode(GL_FRONT, GL_LINE)
    glPolygonMode(GL_BACK, GL_LINE)
    glShadeModel(GL_SMOOTH)

    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()

    gluPerspective(45.0, float(Width)/float(Height), 0.1, 100.0)

    glMatrixMode(GL_MODELVIEW)

#resize openGl
def ReSizeGLScene(Width, Height):
    if Height == 0:
        Height = 1
    glViewport(0, 0, Width, Height)
    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    gluPerspective(45.0, float(Width)/float(Height), 0.1, 100.0)
    glMatrixMode(GL_MODELVIEW)

#Draw all the parts for the window 2D
def draw3D():
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) # clear the screen
    glMatrixMode(GL_MODELVIEW)

    glLoadIdentity()

    gluLookAt(
    xx, 1, zz,
    xx+lx, yy+ly, zz+lz,
    0, 1.0, 0)

    drawAxis3D()
    glutKeyboardFunc(keyPressed)
    glTranslatef(0.0, 0.0, 0.0)

    glRotatef(x_rot, 1.0, 0.0, 0.0)
    glRotatef(y_rot, 0.0, 1.0, 0.0)

    drawShape3D(titik)
    glFlush()
    glutSwapBuffers()

#do all commands for 3D program
def doCommand3D():
    comm = input()
    if (comm == "translate"):
        x, y, z = input().split()
        P = translate3D(titik, float(x), float(y), float(z))
    elif (comm == "dilate"):
        n = input()
        P = dilate3D(titik, float(n))
    elif (comm == "reflect"):
        x, y, z = input().split()
        P = reflect3D(titik, float(x), float(y), float(z))
    elif (comm == "shear"):
        a, b, c = input().split()
        P = shear3D(titik, a, float(b), float(c))
    elif (comm == "rotate"):
        a, deg = input().split()
        P = rotate3D(titik,a, float(deg))
    elif (comm == "stretch"):
        param, x = input().split()
        P = stretch3D(titik, param, float(x))
    elif (comm == "custom"):
        a, b, c = input().split()
        d, e, f = input().split()
        g, h, i = input().split()
        P = custom3D(titik, float(a), float(b), float(c), float(d), float(e), float(f), float(g), float(h), float(i))
    elif (comm == "reset"):
        P = reset3D(titik, initial)
    elif (comm == "multiple"):
        n = input()
        for i in range(0, int(n)):
            doCommand3D()
    elif (comm == "exit"):
        sys.exit()

#keypressed procedure for camera's looking in 3D
def keyPressed(*args):
    global tra_x
    global tra_y
    global tra_z
    global x_rot
    global y_rot
    global lx, lz, xx, zz, ly, yy
    global angle
    fraction = 0.1
    if (args[0] == b"w"):
        xx += lx * fraction
        zz += lz * fraction
    elif (args[0] == b"s"):
        xx -= lx * fraction
        zz -= lz * fraction
    elif (args[0] == b"a"):
        angle -= 0.1
        lx = math.sin(angle)
        lz = -math.cos(angle)
    elif (args[0] == b"d"):
        angle += 0.1
        lx = math.sin(angle)
        lz = -math.cos(angle)
    elif (args[0] == b"z"):
        yy += ly * fraction
        # xx += lx * fraction
    elif (args[0] == b"c"):
        yy -= ly * fraction
        # xx -= lx * fraction
    elif (args[0] == b"u"):
        x_rot += 2.0
        y_rot += 2.0
    elif (args[0] == b"y"):
        x_rot -= 2.0
        y_rot -= 2.0
    elif (args[0] == b"x"):
        global Wireframe
        if Wireframe==False:
            glPolygonMode(GL_FRONT, GL_LINE)
            glPolygonMode(GL_BACK, GL_LINE)
            Wireframe=True
        elif Wireframe ==True:
            glPolygonMode(GL_FRONT, GL_FILL)
            glPolygonMode(GL_BACK, GL_FILL)
            Wireframe=False
    elif(args[0] == b"f"):
        doCommand3D()

#procedure for drawing 3D object
def drawShape3D(x):
    glBegin(GL_QUADS)
    glColor3f(1.0, 0.0,  0.0)

    #front
    glVertex3f(x.arrPoint[0].x, x.arrPoint[0].y, x.arrPoint[0].z)
    glColor3f(0.0, 1.0, 0.0)
    glVertex3f(x.arrPoint[1].x, x.arrPoint[1].y, x.arrPoint[1].z)
    glVertex3f(x.arrPoint[2].x, x.arrPoint[2].y, x.arrPoint[2].z)
    glColor3f(1.0, 0.0, 0.0)
    glVertex3f(x.arrPoint[3].x, x.arrPoint[3].y, x.arrPoint[3].z)

    #back
    glVertex3f(x.arrPoint[4].x, x.arrPoint[4].y, x.arrPoint[4].z)
    glVertex3f(x.arrPoint[5].x, x.arrPoint[5].y, x.arrPoint[5].z)
    glColor3f(0.0, 1.0, 0.0)
    glVertex3f(x.arrPoint[6].x, x.arrPoint[6].y, x.arrPoint[6].z)
    glVertex3f(x.arrPoint[7].x, x.arrPoint[7].y, x.arrPoint[7].z)

    glColor3f(0.0, 1.0, 0.0)

    #left
    glVertex3f(x.arrPoint[8].x, x.arrPoint[8].y, x.arrPoint[8].z)
    glVertex3f(x.arrPoint[9].x, x.arrPoint[9].y, x.arrPoint[9].z)
    glColor3f(0.0, 0.0, 1.0)
    glVertex3f(x.arrPoint[10].x, x.arrPoint[10].y, x.arrPoint[10].z)
    glColor3f(1.0, 0.0, 0.0)
    glVertex3f(x.arrPoint[11].x, x.arrPoint[11].y, x.arrPoint[11].z)

    #right
    glVertex3f(x.arrPoint[12].x, x.arrPoint[12].y, x.arrPoint[12].z)
    glVertex3f(x.arrPoint[13].x, x.arrPoint[13].y, x.arrPoint[13].z)
    glColor3f(0.0, 1.0, 0.0)
    glVertex3f(x.arrPoint[14].x, x.arrPoint[14].y, x.arrPoint[14].z)
    glColor3f(0.0, 0.0, 1.0)
    glVertex3f(x.arrPoint[15].x, x.arrPoint[15].y, x.arrPoint[15].z)

    glColor3f(0.0, 0.0, 1.0)

    #top
    glVertex3f(x.arrPoint[16].x, x.arrPoint[16].y, x.arrPoint[16].z)
    glVertex3f(x.arrPoint[17].x, x.arrPoint[17].y, x.arrPoint[17].z)
    glColor3f(0.0, 1.0, 0.0)
    glVertex3f(x.arrPoint[18].x, x.arrPoint[18].y, x.arrPoint[18].z)
    glVertex3f(x.arrPoint[19].x, x.arrPoint[19].y, x.arrPoint[19].z)
    glColor3f(1.0, 0.0, 0.0)

    #bottom
    glVertex3f(x.arrPoint[20].x, x.arrPoint[20].y, x.arrPoint[20].z)
    glColor3f(0.0, 0.0, 1.0)
    glVertex3f(x.arrPoint[21].x, x.arrPoint[21].y, x.arrPoint[21].z)
    glVertex3f(x.arrPoint[22].x, x.arrPoint[22].y, x.arrPoint[22].z)
    glVertex3f(x.arrPoint[23].x, x.arrPoint[23].y, x.arrPoint[23].z)

    glEnd()

#list of command
def listofcommand():
    print("Untuk list of command 2D, pilih 2")
    print("Untuk list of command 3D, pilih 3")
    print("Masukkan pilihan: ", end = " ")
    n = int(input())
    if (n == 2):
        print("Untuk 2D:")
        print("Pisahkan kata kunci perintah dengan newline")
        print("contoh perintah: ")
        print("translate")
        print("1 2")
        print("")
        print("----List Perintah")
        print("1. translate dx dy")
        print("   Translasi objek dengan menggeser nilai x sebesar dx dan menggeser y sebesar dy")
        print("2. dilate k")
        print("   melakukan dilatasi untuk objek dengan faktor scalling k")
        print("3. rotate deg a b")
        print("   melakukan rotasi objek berlawanan arah jarum jam sebesar deg derajat terhadap titik (a,b)")
        print("4. reflect (param)")
        print("   melakukan pencerminan dengan parameter tertentu seperti y = x, y = -x, atau terhadap titik (a,b)")
        print("5. shear x k")
        print("   melakukan operasi shear pada objek terhadap sumbu x dengan faktor scalling k")
        print("6. stretch x k")
        print("   melakukan operasi stretch pada objek terhadap sumbu x dengan faktor scalling k")
        print("7. custom a b c d")
        print("   melakukan transformasi linier dengan matriks transformasi [(a,b),(c,d)]")
        print("8. multiple n")
        print("   melakukan transformasi linier pada objek sebanyak n kali")
    else:
        print("Untuk 3D:")
        print("Pisahkan kata kunci perintah dengan newline")
        print("contoh perintah: ")
        print("translate")
        print("1 2 3")
        print("")
        print("----List Perintah")
        print("1. translate dx dy dz")
        print("   Translasi objek dengan menggeser nilai x sebesar dx dan menggeser y sebesar dy dan menggeser z sebesar dz")
        print("2. dilate k")
        print("   melakukan dilatasi untuk objek dengan faktor scalling k")
        print("3. rotate a deg")
        print("   melakukan rotasi objek berlawanan arah jarum jam sebesar deg derajat terhadap sumbu-a")
        print("4. reflect x y z")
        print("   melakukan pencerminan terhadap titik (x,y,z)")
        print("5. shear x k1 k2")
        print("   melakukan operasi shear pada objek terhadap sumbu x dengan faktor scalling k1 dan k2")
        print("6. stretch x k")
        print("   melakukan operasi stretch pada objek terhadap sumbu x dengan faktor scalling k")
        print("7. custom a b c d e f g h i")
        print("   melakukan transformasi linier dengan matriks transformasi [(a,b,c),(d,e,f),(g,h,i)]")
        print("8. multiple n")
        print("   melakukan transformasi linier pada objek sebanyak n kali")
    print("Untuk kembali ke program utama, pilih 1")
    print("Masukkan pilihan: ", end = " ")
    n = int(input())
    if (n == 1):
        mainProgram()


#Main program (include making the windows)
def mainProgram():
    print("Selamat datang pada program simulasi transformasi linier")
    print("Terdapat dua menu pada program ini yaitu simulasi 2 dimensi dan 3 dimensi")
    print("1. Untuk menampilkan list of command, silakan input 1")
    print("2. Untuk Memilih 2 dimensi, silakan input 2")
    print("3. Untuk Memilih 3 dimensi, silakan input 3")
    print("Masukan pilihan : ")
    x = int(input())
    if (x == 1):
        listofcommand()
    elif (x == 2):
        run2d()
    else:
        run3d()

#run the main program
mainProgram()