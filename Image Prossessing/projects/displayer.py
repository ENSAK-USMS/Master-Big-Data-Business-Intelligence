import cv2
import os


filepath = 'C:/Git-Hub/Master-ENSAK/Image Prossessing/projects/MGD/MGD2020/ImageSets/train.txt'
images_path = 'C:/Git-Hub/Master-ENSAK/Image Prossessing/projects/MGD/MGD2020/JPEGImages'
annotations_path = 'C:/Git-Hub/Master-ENSAK/Image Prossessing/projects/MGD/MGD2020/Annotations'

def read_file_into_list(filepath):
    with open(filepath, 'r') as file:
        content = file.readlines()
    # you may also want to remove whitespace characters like `\n` at the end of each line
    content = [x.strip() for x in content]
    return content

content_list = read_file_into_list(filepath)





# Iterate over the content list
def get_image(filename):
    # Construct the full image path
    full_path = os.path.join(images_path, filename + '.jpg')
    
    # Read the image using OpenCV
    image = cv2.imread(full_path)
    
    return image

noneExsitingImages = []
for filename in content_list:
    if not os.path.exists(os.path.join(images_path, filename + '.jpg')):
        noneExsitingImages.append(filename)
        
print(noneExsitingImages)

noneLabelledImages = []
for filename in content_list:
    if not os.path.exists(os.path.join(annotations_path, filename + '.xml')):
        noneLabelledImages.append(filename)

print(noneLabelledImages)
    

# read the xml file and get xmin, ymin, xmax, ymax
import xml.etree.ElementTree as ET

def parse_annotation(annotation_file):
    tree = ET.parse(annotation_file)
    root = tree.getroot()

    # Find the 'object' tag
    for obj in root.iter('object'):
        # Find the 'bndbox' tag
        for box in obj.findall('bndbox'):
            # Extract the coordinates
            xmin = int(box.find('xmin').text)
            ymin = int(box.find('ymin').text)
            xmax = int(box.find('xmax').text)
            ymax = int(box.find('ymax').text)

    return xmin, ymin, xmax, ymax

import cv2
import os
from PIL import Image, ImageTk
from tkinter import Tk, Label, Button

import xml.etree.ElementTree as ET

class ImageDisplayer:
    def __init__(self, master):
        self.master = master
        self.index = 0
        self.image_label = Label(master)
        self.image_label.pack()

        self.next_button = Button(master, text="Next", command=self.next_image)
        self.next_button.pack()

        self.prev_button = Button(master, text="Previous", command=self.prev_image)
        self.prev_button.pack()

        self.display_image()

    def next_image(self):
        self.index += 1
        self.display_image()

    def prev_image(self):
        self.index -= 1
        self.display_image()

    def display_image(self):
        filename = content_list[self.index]
        image = get_image(filename)
        xmin, ymin, xmax, ymax = parse_annotation(os.path.join(annotations_path, filename + '.xml'))
        cv2.rectangle(image, (xmin, ymin), (xmax, ymax), (0, 255, 0), 2)
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)  # Convert the image from BGR to RGB
        image = Image.fromarray(image)  # Convert the image to a PIL image
        photo = ImageTk.PhotoImage(image)  # Convert the PIL image to a PhotoImage
        self.image_label.config(image=photo)
        self.image_label.image = photo  # Keep a reference to the image to prevent it from being garbage collected

root = Tk()
app = ImageDisplayer(root)
root.mainloop()
