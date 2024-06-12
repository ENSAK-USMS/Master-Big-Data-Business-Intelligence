import os
import cv2
import numpy as np
from skimage import feature
from skimage.feature import hog
from tqdm import tqdm
import sys
import random

def process_image(image_path):
    """Process an image to extract advanced features including Local Binary Patterns and Histogram of Oriented Gradients."""
    img = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)
    if img is None:
        print(f"Failed to load {image_path}", file=sys.stderr)
        return [0] * (29 + 36)  # Return a list of zeros if the image fails to load (LBP features + HOG features)

    img = cv2.resize(img, (128, 128))  # Standard size
    edges = cv2.Canny(img, 100, 200)  # Canny edge detection

    # Local Binary Patterns
    lbp = feature.local_binary_pattern(img, P=24, R=3, method='uniform')
    (lbp_hist, _) = np.histogram(lbp.ravel(), bins=np.arange(0, 27), range=(0, 26))
    lbp_hist = lbp_hist.astype("float")
    lbp_hist /= (lbp_hist.sum() + 1e-6)  # Normalize the histogram

    mean_val = np.mean(img)
    std_val = np.std(img)
    mean_edges = np.mean(edges)

    return [mean_val, std_val, mean_edges] + lbp_hist.tolist()

def create_arff_file(gun_folder, not_gun_folder, output_file="output.arff", max_images=None):
    data = []
    # Process 'gun' images
    gun_files = [f for f in os.listdir(gun_folder) if f.lower().endswith(('png', 'jpg', 'jpeg', 'bmp'))]
    not_gun_files = [f for f in os.listdir(not_gun_folder) if f.lower().endswith(('png', 'jpg', 'jpeg', 'bmp'))]

    # Limit the number of images if max_images is set
    if max_images is not None:
        gun_files = gun_files[:max_images]
        not_gun_files = not_gun_files[:max_images]

    for filename in tqdm(gun_files, desc="Processing Gun Images"):
        filepath = os.path.join(gun_folder, filename)
        features = process_image(filepath)
        data.append(features + ['gun'])

    for filename in tqdm(not_gun_files, desc="Processing Not-Gun Images"):
        filepath = os.path.join(not_gun_folder, filename)
        features = process_image(filepath)
        data.append(features + ['not-gun'])

    # Shuffle the complete data list
    random.shuffle(data)

    # Write to ARFF
    with open(output_file, 'w') as f:
        f.write('@RELATION image_features\n')
        f.write('@ATTRIBUTE mean_gray_value REAL\n')
        f.write('@ATTRIBUTE std_gray_value REAL\n')
        f.write('@ATTRIBUTE mean_edge_value REAL\n')
        for i in range(26):
            f.write(f'@ATTRIBUTE lbp_histogram_{i} REAL\n')
        for i in range(36):  # Assuming 36 features from HOG
            f.write(f'@ATTRIBUTE hog_feature_{i} REAL\n')
        f.write('@ATTRIBUTE class {gun,not-gun}\n')
        f.write('@DATA\n')
        for features in data:
            f.write(','.join(str(feat) for feat in features) + '\n')

if __name__ == "__main__":
    gun_folder = './classification/train/gun'  # Modify this path
    not_gun_folder = './classification/train/not-a-gun'  # Modify this path
    max_images_per_class = 500  # Modify as needed
    create_arff_file(gun_folder, not_gun_folder, max_images=max_images_per_class)
