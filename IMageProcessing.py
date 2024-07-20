import numpy as np
import matplotlib.pyplot as plt
from skimage import color, filters
from PIL import Image

image_path = 'IMG_5798.jpeg'
image = Image.open(image_path)

gray_image = color.rgb2gray(np.array(image))  

threshold_value = filters.threshold_otsu(gray_image)
binary_image = gray_image > threshold_value


fig, axes = plt.subplots(1, 2, figsize=(10, 5))

axes[0].imshow(image)
axes[0].set_title("Original image")

axes[1].imshow(binary_image, cmap='gray')
axes[1].set_title("New Thresholded image")


plt.savefig('New.png')
plt.show()
