### Test scenario:
1. Get first 10 Mars photos made by "Curiosity" on 1000 sol.
2. Get first 10 Mars photos made by "Curiosity" on earth date that is equals 1000 Mars sol.
3. Compare images and metadata from API. Test fails in case if any difference.
Please use standard technologies, tools and frameworks
which you are usually use in test frameworks creation.

### As a plus:
Using NASA’s API determine how many pictures were made by each camera (by Curiosity on 1000 sol.).
If any camera made 10 times less images than any other - test fails.

### Run from command line:
Open Terminal and navigate project directory. Then
1) _mvn integration-test serenity:aggregate_
2) _find html report in /target/site/ then open in browser index.html_
