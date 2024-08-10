import pandas as pd
from scipy.io import arff
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score, confusion_matrix
import sys

def load_data(arff_path):
    """ Load ARFF file and return a DataFrame. """
    try:
        data, meta = arff.loadarff(arff_path)
        df = pd.DataFrame(data)
        df['class'] = df['class'].str.decode('utf-8')  # Decode byte strings to normal strings
        return df
    except Exception as e:
        print(f"Failed to load data: {str(e)}", file=sys.stderr)
        return None

def train_random_forest(df):
    """ Train a Random Forest classifier. """
    # Split the data into features and labels
    X = df.drop('class', axis=1)
    y = df['class']
    
    # Split the dataset into training and testing sets
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    
    # Initialize and train the Random Forest classifier
    clf = RandomForestClassifier(n_estimators=100, random_state=42)
    clf.fit(X_train, y_train)
    
    # Predict on the test set
    y_pred = clf.predict(X_test)
    
    # Calculate accuracy and confusion matrix
    accuracy = accuracy_score(y_test, y_pred)
    cm = confusion_matrix(y_test, y_pred)
    
    return accuracy, cm

def main(arff_file):
    df = load_data(arff_file)
    if df is not None:
        accuracy, cm = train_random_forest(df)
        print(f"Accuracy: {accuracy * 100:.2f}%")
        print("Confusion Matrix:")
        print(cm)

if __name__ == "__main__":
    arff_file = './output.arff'  # Adjust this path
    main(arff_file)
