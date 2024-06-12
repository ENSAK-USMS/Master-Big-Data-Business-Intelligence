function suggestNextWord() {
    const inputText = document.getElementById('inputText').value.trim();
    const resultDiv = document.getElementById('result');
    const nextWordParagraph = document.getElementById('nextWord');

    console.log('inputText: ' + inputText);
    if (inputText) {
        fetch('http://127.0.0.1:8000/recommend_next_word', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ search_text: inputText })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                resultDiv.classList.remove('hidden');
                nextWordParagraph.textContent = data.next_word;
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
                resultDiv.classList.remove('hidden');
                nextWordParagraph.textContent = 'Error: ' + error.message;
            });
    } else {
        resultDiv.classList.add('hidden');
        nextWordParagraph.textContent = '';
    }
}
