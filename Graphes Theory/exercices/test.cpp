#include<bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int Q;
    cin >> Q;

    unordered_map<int, int> freq;
    multiset<pair<int, int>> max_freq;

    while(Q--) {
        int type;
        cin >> type;

        if(type == 1) {
            int X;
            cin >> X;

            if(freq.count(X)) {
                max_freq.erase(max_freq.find({freq[X], X}));
            }

            freq[X]++;
            max_freq.insert({freq[X], X});
        } else {
            if(max_freq.empty()) {
                cout << "empty\n";
            } else {
                auto it = prev(max_freq.end());
                cout << it->second << "\n";

                if(freq[it->second] > 1) {
                    freq[it->second]--;
                } else {
                    freq.erase(it->second);
                }

                max_freq.erase(it);
            }
        }
    }

    return 0;
}