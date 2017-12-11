comsol=/path/to/comsol
class=preCOMSOL
$comsol compile  -jdkroot /usr/java/jdk1.7.0_03/ \
	-classpathadd /path/to/sweepgen/package/ $class.java
echo "Run $class.class"
$comsol batch -dev /path/to/sweepgen/package/ -inputfile $class.class
rm $class.class.*