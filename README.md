# JNBIS
Java Implementation of NIST Biometric Image Software (NBIS) 

![CI](https://github.com/mhshams/jnbis/actions/workflows/ci.yaml/badge.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mhshams/jnbis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.mhshams/jnbis)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

> 🛠️ **NOTE:** Due to lack of time, this project is in maintenance mode. Only critical bugs will be fixed. Pull requests are always welcome!

### About JNBIS
JNBIS is a library, written in Java, to extract and decode NIST (National Institute of Standards and Technology) compressed files and WSQ (Wavelet Scalar Quantization) images. 
The code has been converted from NBIS (NIST Biometric Image Software) version 1.1 which is written in C.
You can find more about NIST Biometric Image Software [here](http://www.nist.gov/itl/iad/ig/nbis.cfm).

### Quick Start
##### Build and Install
JNBIS is available in [the maven central repository](http://search.maven.org/#browse), so you just need to download and add it to your project libraries or 
if you are using maven, add it to project dependencies.
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mhshams/jnbis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.mhshams/jnbis)

```xml
<dependency>
  <groupId>com.github.mhshams</groupId>
  <artifactId>jnbis</artifactId>
  <version>2.x.x</version>
</dependency>
```

Alternatively, you can clone the source code and build it with maven. You need **JDK** version **1.8 or higher** to build the code. 
```bash
$ git clone git@github.com:mhshams/jnbis.git
$ cd jnbis
$ mvn package
```
### Examples
##### WSQ Decoding 
Convert WSQ image to PNG image and return the result as **File**
```Java
File png = Jnbis.wsq()
                .decode("path/to/wsq/file.wsq")
                .toPng()
                .asFile("/path/to/final/file.png");
```
Convert WSQ image to GIF image and return the result as **File**
 ```Java
File gif = Jnbis.wsq()
                .decode(new File("path/to/wsq/file.wsq"))
                .toGif()
                .asFile("/path/to/final/file.gif");
```
Convert WSQ image (as input stream) to JPEG image and return the result as **File**
```Java
File jpg = Jnbis.wsq()
                .decode(wsqInputStream)
                .toJpg()
                .asFile("/path/to/final/file.jpg");
 ```
 Convert WSQ image to PNG image and return the result as **InputStream** 
```Java
 InputStream pngStream = Jnbis.wsq()
                              .decode("path/to/wsq/file.wsq")
                              .toPng()
                              .asInputStream();
```
Convert WSQ image to GIF image and return the result as **Byte Array**
```Java
byte[] gifBytes = Jnbis.wsq()
                       .decode(new File("path/to/wsq/file.wsq"))
                       .toGif()
                       .asByteArray();
```
 
For more examples check the **SampleWsqTest.java** in the project source. 
##### NIST Decoding 
Decode a NIST file with given file name
```Java
Nist nist = Jnbis.nist().decode("/path/to/nist/file"));
```

Decode a NIST file with given **File** instance
```Java
Nist nist = Jnbis.nist().decode(new File("/path/to/nist/file"));
```

Decode a NIST file with given **InputStream** instance
```Java
Nist nist = Jnbis.nist().decode(nistInputStream));
```

**Nist** instance contains different types of data, depending on file type. 
Here is a sample code that extract all fingerprints and save them in individual files. 
```Java
Nist nist = Jnbis.nist().decode(new File("/path/to/nist/file"));

for (HighResolutionGrayscaleFingerprint fp : nist.getHiResGrayscaleFingerprints()) {
    Jnbis.wsq()
        .decode(fp.getImageData())
        .toPng()
        .asFile("/path/fp-" + fp.getImageDesignationCharacter() + ".png");
}
 ```
For more examples check the **SampleNistTest.java** and **AnsiReferencesTest.java** in the project source. 
