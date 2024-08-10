import pandas as pd
from collections import defaultdict, Counter
import random
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware

# Load the cleaned comments data
file_path = '../TP 5/youtube_comments_cleaned.xlsx'
df = pd.read_excel(file_path)

# Function to create bi-grams from text
def create_bigrams(text):
    words = text.split()
    return [(words[i], words[i + 1]) for i in range(len(words) - 1)]

# Create a defaultdict to store bi-gram frequencies
bigram_freq = defaultdict(Counter)

# Calculate the frequency of each bi-gram
for comment in df['Comment_cleaned']:
    if type(comment) != str or len(comment) == 0:
        continue
    for w1, w2 in create_bigrams(comment):
        bigram_freq[w1][w2] += 1

# Function to recommend the next word
def recommend_next_word(last_word):
    if last_word in bigram_freq:
        next_word_candidates = bigram_freq[last_word]
        total_count = sum(next_word_candidates.values())
        next_word_probs = {word: count / total_count for word, count in next_word_candidates.items()}
        next_word = random.choices(list(next_word_probs.keys()), weights=next_word_probs.values())[0]
        return next_word
    else:
        return None

# Create FastAPI app
app = FastAPI()

# Configure CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allows all origins
    allow_credentials=True,
    allow_methods=["*"],  # Allows all methods
    allow_headers=["*"],  # Allows all headers
)

# Create a request body model
class SearchText(BaseModel):
    search_text: str

@app.post("/recommend_next_word/")
def get_next_word(search_text: SearchText):
    input_string = search_text.search_text
    words = input_string.split(' ')
    if not words:
        raise HTTPException(status_code=400, detail="Input string must contain at least one word")
    
    last_word = words[-1]
    next_word = recommend_next_word(last_word)
    if next_word:
        return {"input_string": input_string, "next_word": next_word}
    else:
        raise HTTPException(status_code=404, detail="Next word not found")

# To run the app, use the following command:
# python -m uvicorn app:app --reload
