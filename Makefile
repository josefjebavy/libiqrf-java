JAVAC=javac
JAVA=java
CC=g++

JavaDirHeader=/usr/lib/jvm/java/include
#pro /jni.h
JavaHeader=${JavaDirHeader}
#platforme zavisly adresar
JavaPlatformHeader=${JavaDirHeader}/linux
#-----------------
DIRJ=cz/xeres/iqrf
JDIRJ=cz.xeres.iqrf
IQRFDIRJ=${DIRJ}/libiqrf
IQRFDIRJH=${JDIRJ}.libiqrf
#-------------------------------------------------

IQRF=${IQRFDIRJ}/Iqrf
IQRFDEV=${IQRFDIRJ}/IqrfDev
#----------------------
LibJavaIqrfName=LibJavaIqrfDev
LibJavaIqrfDev=${IQRFDIRJ}/${LibJavaIqrfName}
#----------------------------------
HeaderLibJavaIqrfDev="cz_xeres_iqrf_libiqrf_LibJavaIqrfDev.h"
#----------------------
LibJavaNamePlatform=libJavaIqrfDev.so
Libs=${LibJavaNamePlatform} 
#----------------------
SourceLibJava="${LibJavaIqrfName}.cpp"

JNI:
	javah -jni ${IQRFDIRJH}.${LibJavaIqrfName}
#-o ${HeaderLibJavaIqrfDev}
	echo It needs to have installed the library libiqrf!

# přepínač "I" uvadi cestu k hlavickovym souborum
# knihovna ktera se menuje a vola jako HelloWorld se musi jmenovat libHelloWorld.so
# musi byt prepinac  - shared aby sla načíst, pask neslo zkompilovat, ale prekladac doporucil prepinac -fPIC

LibJava:
	${CC} ${SourceLibJava}  \
		-o ${LibJavaNamePlatform}  \
		-I${JavaHeader} -I${JavaPlatformHeader} \
		 -shared -fPIC -lstdc++  -liqrf -lusb
# -L/usr/local/lib -liqrf	
#-fstack-protector --param=ssp-buffer-size=4 
all:
	JNI LibJava