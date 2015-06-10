#JNBIS
Java Implementation of NIST Biometric Image Software (NBIS) 
[![License](https://pypip.in/license/apache-libcloud/badge.png)]() [![Build Status](https://travis-ci.org/mhshams/jnbis.svg?branch=master)](https://travis-ci.org/mhshams/jnbis)

###About JNBIS
JNBIS is a library, written in Java, to extract and decode NIST (National Institute of Standards and Technology) compressed files and WSQ (Wavelet Scalar Quantization) images. 
The code has been converted from NBIS (NIST Biometric Image Software) version 1.1 which is written in C.
You can find more about NIST Biometric Image Software [here](http://www.nist.gov/itl/iad/ig/nbis.cfm).

###Quick Start
#####Build and Install
JNBIS is available in [The Central Repository](http://search.maven.org/#browse), so you just need to include it to your project libraries or maven dependencies.
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mhshams/jnbis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.mhshams/jnbis)

```xml
<dependency>
  <groupId>jnbis</groupId>
  <artifactId>jnbis</artifactId>
  <version>1.0.6</version>
</dependency>
```

Alternatively, you can clone the source code and build it with maven. You need JDK version *1.7 or higher* to build the code. 
```bash
$ git clone git@github.com:mhshams/jnbis.git
$ cd jnbis
$ mvn install
```
#####WSQ Decoder 
First of all you need to create an instance of _org.jnbis.WSqDecoder_.
 ```Java
WsqDecoder wsqDecoder = new WsqDecoder();
 ```
Then you can decode your WSQ image using following methods:
 ```Java
//Decode a WSQ image by passing image file name
public Bitmap decode(String fileName) throws IOException;

//Decode a WSQ image by passing image File (java.io.File).
public Bitmap decode(File file) throws IOException;

//Decode a WSQ image by passing image stream.
public Bitmap decode(InputStream inputStream) throws IOException;

//Decode a WSQ image by passing image byte array.
public Bitmap decode(final byte[] data);
 ```
The result of all methods is a Bitmap object which contains the decoded data in byte array format. you can convert it to other image formats using _org.jnbis.ImageUtils_ class.
```Java
WsqDecoder wsqDecoder = new WsqDecoder();
ImageUtils imageUtils = new ImageUtils();   
 
Bitmap bm = wsqDecode.docode("/path/to/my/wsq-image");
byte[] data = imageUtils.bitmap2jpeg(bm);

FileOutputStream bos = new FileOutputStream("myjpeg.jpg");
bos.write(data);
bos.close();
```
#####NIST Decoder
 Just like WsqDecoder, you need to create an instance of _org.jnbis.nist.NistDecoder_.
 ```Java
NistDecoder nistDecoder = new NistDecoder();
 ```

Now you can decode your NIST file using following methods:

```Java
//Decode a NIST file by passing file name.
public DecodedData decode(String fileName, DecodedData.Format fingerImageFormat) throws IOException;

//Decode a NIST file by passing File (java.io.File).
public DecodedData decode(File file, DecodedData.Format fingerImageFormat) throws IOException;

//Decode a NIST file by passing stream.
public DecodedData> decode(InputStream inputStream, DecodedData.Format fingerImageFormat) throws IOException;

//Decode a NIST file by passing byte array.
public DecodedData decode(final byte[] data, DecodedData.Format fingerImageFormat);
```
Second parameter in above methods is the output image format and currently JPEG, GIF and PNG are supported.
Decoded

DecodedData contains different types of data, depending on file type. 
Here is a sample code to extract all fingerprints and save them in separate files. 
```Java
DecodedData decoded = new NistDecoder().decode("/path/to/nist-file", DecodedData.Format.GIF);

for (Integer key : decoded.getBinaryKeys()) {
  HighResolutionGrayscaleFingerprint image = decoded.getHiResGrayscaleFingerprint(key);
  FileOutputStream bos = new FileOutputStream(file + "-" + key + ".gif");
  bos.write(image.getImageData());
  bos.close();
}
```
