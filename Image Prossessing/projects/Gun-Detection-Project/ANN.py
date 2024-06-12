import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
import pandas as pd
from scipy.io import arff
import numpy as np

def load_data(arff_path):
    """ Load ARFF file and return features and labels. """
    data, meta = arff.loadarff(arff_path)
    df = pd.DataFrame(data)
    df['class'] = df['class'].str.decode('utf-8')  # Decode byte strings
    X = df.drop('class', axis=1).values.astype(np.float32)
    y = df['class'].values
    return X, y

def preprocess_data(X, y):
    """ Preprocess data, normalize X and encode y. """
    X_normalized = X / 255.0  # Simple normalization
    encoder = LabelEncoder()
    y_encoded = encoder.fit_transform(y)
    return X_normalized, y_encoded

def build_model(input_shape):
    """ Build a simple ANN model. """
    model = Sequential([
        Dense(128, activation='relu', input_shape=(input_shape,)),
        Dense(64, activation='relu'),
        Dense(2, activation='softmax')  # For binary classification
    ])
    return model

def main(arff_file):
    # Load and preprocess data
    X, y = load_data(arff_file)
    print(len(X))
    X, y = preprocess_data(X, y)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # Build and compile the model
    model = build_model(X_train.shape[1])
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    
    # Train the model
    model.fit(X_train, y_train, epochs=10, batch_size=32, validation_split=0.1)

    # Evaluate the model
    loss, accuracy = model.evaluate(X_test, y_test)
    print(f"Test Accuracy: {accuracy * 100:.2f}%")

if __name__ == "__main__":
    arff_file = 'C:/Git-Hub/Master-ENSAK/Image Prossessing/projects/output.arff'  # Adjust this path
    main(arff_file)
