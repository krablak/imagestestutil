# ImagesTestUtil 1.0.0
## Overview
Simple helper methods for making testing of image manipulation little bit easier. Motivation for this tool could be found in [this blog post](http://techkrab.tumblr.com/post/126267843884/w32).

## Usage
### Load Image And Assert

		// Load image using ImagesTestUtil
		BufferedImage img = ImagesTestUtil.loadImage("/bugs/MD108/large-image-0.jpg");
		// Run tested code
		BufferedImage previewImage = ImageThumbnailServiceHelper.resizeAndCropImage(img, 20, 20, Mode.AUTOMATIC);
		// Assert tested code result using ImagesTestUtil digest
		assertEquals("nDGna6JX95MgsdITq04lsA==", ImagesTestUtil.toStringDigest(previewImage));

### View Image As String

		BufferedImage img = loadImage("/bugs/MD108/10_Group_commented_2.png");
		System.out.println(ImagesTestUtil.toRGBString(img));

### Save Image To Temporary Directory

		BufferedImage img = loadImage("/bugs/MD108/10_Group_commented_2.png");
		System.out.println(ImagesTestUtil.saveToTemp(img, "png"));


## Licenses
Please, respect licenses of all used project and technologies. Code of this project itself is licensed under a [Creative Commons Attribution-ShareAlike 4.0 International License](http://creativecommons.org/licenses/by-sa/4.0/).

![](http://i.creativecommons.org/l/by-sa/4.0/88x31.png)
