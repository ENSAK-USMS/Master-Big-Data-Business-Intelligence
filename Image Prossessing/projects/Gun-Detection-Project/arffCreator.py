import os
from PIL import Image
import numpy as np
import sys
from tqdm import tqdm  # for progress bar

def extract_features(image_path):
    """Extract features from an image."""
    try:
        with Image.open(image_path) as img:
            img = img.convert('RGB')  # Ensure image is in RGB format.
            # Example feature: mean color values
            np_img = np.array(img)
            mean_colors = np_img.mean(axis=(0, 1))
            return mean_colors
    except Exception as e:
        print(f"Error processing {image_path}: {str(e)}", file=sys.stderr)
        return [0, 0, 0]  # Return a default feature if error.

def create_arff_file(data_folder, output_file="output.arff"):
    """Create an ARFF file from images in a specified folder for clustering."""
    data = []
    files = [f for f in os.listdir(data_folder) if f.lower().endswith(('png', 'jpg', 'jpeg', 'bmp'))]
    for filename in tqdm(files, desc="Processing Images", file=sys.stdout):
        filepath = os.path.join(data_folder, filename)
        features = extract_features(filepath)
        data.append(features)

    # Write to ARFF
    with open(output_file, 'w') as f:
        f.write('@RELATION image_features\n')
        f.write('@ATTRIBUTE mean_red REAL\n')
        f.write('@ATTRIBUTE mean_green REAL\n')
        f.write('@ATTRIBUTE mean_blue REAL\n')
        f.write('@DATA\n')
        for features in data:
            f.write(','.join(str(feat) for feat in features) + '\n')

# Example usage
if __name__ == "__main__":
    data_folder = 'C:/Git-Hub/Master-ENSAK/Image Prossessing/projects/MGD/MGD2020/JPEGImages'  # Modify this path
    create_arff_file(data_folder)
