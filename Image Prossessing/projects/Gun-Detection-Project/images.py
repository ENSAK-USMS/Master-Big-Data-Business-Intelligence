import tkinter as tk
from tkinter import filedialog, ttk
from PIL import Image, ImageTk
import os
import cv2
import numpy as np
import xml.etree.ElementTree as ET
import threading
import re


class ImageLabelViewer:
    """A GUI application to display images and their annotations."""

    def __init__(self, root):
        self.root = root
        self.image_list = []
        self.xml_list = []
        self.txt_list = []
        self.current_index = 0
        self.dataset_title = ""  # New attribute for the dataset title

        self.style = ttk.Style()
        self.style.configure("TButton", font=("Arial", 10), padding=10)

        self.frame = ttk.Frame(root)
        self.frame.grid(column=0, row=0, sticky=(tk.W, tk.E, tk.N, tk.S))

        # New label for the dataset title
        self.dataset_title_label = ttk.Label(self.frame, text="Dataset Title")
        self.dataset_title_label.grid(column=0, row=0, columnspan=3)

        self.image_label = ttk.Label(self.frame)
        self.image_label.grid(column=0, row=1, columnspan=3)

        # New label for the image name
        self.image_name_label = ttk.Label(self.frame, text="")
        self.image_name_label.grid(column=0, row=3, columnspan=3)

        self.prev_button = ttk.Button(
            self.frame, text="Previous", command=self.show_previous_image)
        self.prev_button.grid(column=0, row=2)

        self.next_button = ttk.Button(
            self.frame, text="Next", command=self.show_next_image)
        self.next_button.grid(column=2, row=2)

        self.browse_button = ttk.Button(
            self.frame, text="Browse Directories", command=self.browse_directories)
        self.browse_button.grid(column=1, row=2)


    def browse_directories(self):
        """Open dialog to select image and label directories."""
        image_dir = filedialog.askdirectory(title="Select Image Directory")
        label_dir = filedialog.askdirectory(title="Select Label Directory")
        self.load_images_and_labels(image_dir, label_dir)


    def load_images_and_labels(self, image_dir, label_dir):
        """Load images and labels from specified directories."""
        # Clear existing data
        self.image_list.clear()
        self.xml_list.clear()
        self.txt_list.clear()
        self.current_index = 0  # Reset the current index

        # Load new images and labels
        for root, dirs, files in os.walk(image_dir):
            for file in files:
                if file.endswith('.jpg') or file.endswith('.png'):
                    self.image_list.append(os.path.join(root, file))
        for root, dirs, files in os.walk(label_dir):
            for file in files:
                if file.endswith('.xml'):
                    self.xml_list.append(os.path.join(root, file))
                elif file.endswith('.txt'):
                    self.txt_list.append(os.path.join(root, file))

        # Custom sorting function to handle numeric parts in filenames
        def custom_sort(filename):
            parts = re.split(r'(\d+)', os.path.basename(filename).lower())
            return [int(part) if part.isdigit() else part for part in parts]

        # Sort the lists by file names using the custom sorting function
        self.image_list.sort(key=custom_sort)
        self.xml_list.sort(key=custom_sort)
        self.txt_list.sort(key=custom_sort)

        # Update the dataset title
        self.dataset_title = os.path.basename(os.path.dirname(image_dir))
        self.dataset_title_label.config(text=self.dataset_title)

        # Display the first image of the new dataset
        if self.image_list:
            self.show_image_and_label(0)



    def display_annotations(self, image_path, xml_annotation_path, txt_annotation_path):
        """Display annotations on the image."""
        image = cv2.imread(image_path, cv2.IMREAD_COLOR)
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

        if xml_annotation_path:
            for xmin, ymin, xmax, ymax in self.parse_annotation(xml_annotation_path):
                cv2.rectangle(image, (xmin, ymin),
                              (xmax, ymax), (0, 255, 0), 2)

        if txt_annotation_path:
            with open(txt_annotation_path, 'r') as file:
                content = file.read()
                annotations = content.split('\n')
                annotations = [list(map(float, a.split()))
                               for a in annotations if a]
                height, width = image.shape[:2]
                annotations = [[a[0], a[1]*width, a[2]*height,
                                a[3]*width, a[4]*height] for a in annotations]
                for a in annotations:
                    cv2.rectangle(image, (int(a[1]-a[3]/2), int(a[2]-a[4]/2)),
                                  (int(a[1]+a[3]/2), int(a[2]+a[4]/2)), (0, 255, 0), 2)

        image = Image.fromarray(image)
        return image

    def parse_annotation(self, annotation_file):
        """Parse annotations from an XML file."""
        tree = ET.parse(annotation_file)
        root = tree.getroot()
        for obj in root.iter('object'):
            for box in obj.findall('bndbox'):
                xmin = int(box.find('xmin').text)
                ymin = int(box.find('ymin').text)
                xmax = int(box.find('xmax').text)
                ymax = int(box.find('ymax').text)
                yield xmin, ymin, xmax, ymax


    def show_image_and_label(self, index):
        """Display the image and its annotations at the given index."""
        if index < 0 or index >= len(self.image_list):
            return

        self.current_index = index
        image_path = self.image_list[index]
        xml_annotation_path = self.xml_list[index] if index < len(
            self.xml_list) else None
        txt_annotation_path = self.txt_list[index] if index < len(
            self.txt_list) else None
        image = self.display_annotations(
            image_path, xml_annotation_path, txt_annotation_path)
        image = image.resize((300, 300))
        photo = ImageTk.PhotoImage(image)
        self.image_label.config(image=photo)
        self.image_label.image = photo

        # Update the image name label
        image_name = os.path.basename(image_path)
        self.image_name_label.config(text=image_name)


    def show_next_image(self):
        """Display the next image in the list."""
        self.show_image_and_label(self.current_index + 1)

    def show_previous_image(self):
        """Display the previous image in the list."""
        self.show_image_and_label(self.current_index - 1)

    def load_images_and_labels_async(self, image_dir, label_dir):
        """Load images and labels asynchronously."""
        threading.Thread(target=self.load_images_and_labels,
                         args=(image_dir, label_dir)).start()


root = tk.Tk()
root.title("Image and Label Display")
app = ImageLabelViewer(root)
root.mainloop()
