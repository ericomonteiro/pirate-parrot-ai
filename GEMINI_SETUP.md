# Google Gemini Setup Guide (FREE!)

## 🎉 Great News!

The application now uses **Google Gemini** by default, which is **completely free** and doesn't require a credit card!

---

## Why Gemini?

### ✅ Advantages

1. **Completely Free**
   - No credit card required
   - No credit limits
   - No expiration

2. **Generous Limits**
   - 60 requests per minute
   - 1,500 requests per day
   - Sufficient for intensive practice

3. **Excellent Quality**
   - Comparable to GPT-4
   - Great for programming problems
   - Clear and detailed explanations

4. **Fast**
   - 3-5 seconds per solution
   - Consistent responses
   - Low latency

5. **Vision Support**
   - Analyze screenshots of coding challenges
   - Extract problems from images
   - Perfect for interview preparation

### ❌ OpenAI (Not Recommended)

- Requires credit card
- No real free tier
- Costs $0.002 per solution
- Limited quota
- No vision in free tier

---

## How to Setup Gemini

### Step 1: Get API Key (2 minutes)

1. **Visit**: https://makersuite.google.com/app/apikey
   
2. **Sign in** with your Google account
   - Any Google account works
   - No special account needed

3. **Click "Create API Key"**
   - Choose "Create API key in new project"
   - Or select an existing project

4. **Copy the key**
   - Starts with something like `AIza...`
   - Save it in a secure location
   - You can view it again later

### Step 2: Configure in App

**Option 1 - App Interface**:
1. Open Interview Assistant
2. Click Settings (⚙️)
3. Paste your Gemini API key
4. Done!

**Option 2 - Environment Variable**:
```bash
export OPENAI_API_KEY="your-gemini-key-here"
./gradlew :composeApp:run
```

> **Note**: Even though it's Gemini, we use the same `OPENAI_API_KEY` variable for compatibility.

### Step 3: Test

1. Search for "Two Sum"
2. Click on the problem
3. Select "Python"
4. Wait 3-5 seconds
5. ✅ Solution generated!

**Or test screenshot feature**:
1. Click the camera icon
2. Screenshot will be captured
3. AI analyzes and generates solution
4. ✅ Works perfectly!

---

## Comparison: Gemini vs OpenAI

| Feature | Google Gemini | OpenAI GPT-3.5 |
|---------|---------------|----------------|
| **Cost** | ✅ Free | ❌ Paid |
| **Credit Card** | ✅ Not needed | ❌ Required |
| **Requests/day** | ✅ 1,500 | ❌ Depends on credits |
| **Quality** | ✅ Excellent | ✅ Good |
| **Speed** | ✅ 3-5s | ✅ 3-5s |
| **Time Limit** | ✅ No limit | ❌ 3 months |
| **Setup** | ✅ 2 minutes | ❌ 10 minutes |
| **Vision** | ✅ Yes | ❌ Not in free tier |
| **Screenshot Analysis** | ✅ Yes | ❌ No |

**Winner**: 🏆 **Google Gemini**

---

## Free Tier Limits

### What you can do

✅ **1,500 requests per day**:
- 1,500 solutions per day
- More than enough for practice
- Resets every day

✅ **60 requests per minute**:
- Generate solutions quickly
- No waiting between requests
- Perfect for study sessions

### Usage Examples

**Interview Preparation** (1 week):
- 50 problems per day
- 350 problems total
- ✅ Well within limits

**Intensive Practice** (1 day):
- 100 problems
- ✅ No problem

**Normal Usage**:
- 10-20 problems per day
- ✅ Perfect

**Screenshot Analysis**:
- Capture and analyze coding challenges
- Each screenshot = 1 request
- ✅ 1,500 analyses per day

---

## Troubleshooting

### "API key not valid"

**Solution**:
1. Check if you copied the complete key
2. Generate a new key at https://makersuite.google.com/app/apikey
3. Make sure Gemini API is enabled

### "Quota exceeded"

**Cause**: You exceeded 1,500 requests in a day

**Solution**:
1. Wait until next day (resets at midnight UTC)
2. Use cached solutions (doesn't count toward limit)
3. The limit is very generous, hard to reach

### "Rate limit exceeded"

**Cause**: More than 60 requests per minute

**Solution**:
1. Wait 1 minute
2. Continue generating solutions
3. Very rare in normal usage

### "Model not found"

**Solution**:
1. Make sure you're using the latest version of the app
2. Model used is `gemini-2.5-flash`
3. Check if API is enabled in Google Cloud Console

### Screenshot analysis fails

**Solution**:
1. Check if image contains readable text
2. Try with a clearer screenshot
3. Verify internet connection
4. Make sure you're using Gemini (not OpenAI)

---

## Tips to Maximize Usage

### 1. Use Cache

- Solutions are automatically cached
- Second view = instant
- Doesn't count toward request limit

### 2. Plan Your Sessions

- Identify problems you want to solve
- Generate all solutions at once
- Study offline afterwards

### 3. Different Languages

- Each language = new request
- Choose 1-2 main languages
- Generate only what you'll use

### 4. Monitor Your Usage

- Gemini doesn't have usage dashboard
- But with 1,500/day, it's hard to exceed
- Use freely!

### 5. Screenshot Feature

- Capture coding challenges from anywhere
- One screenshot = one request
- Perfect for interview practice

---

## Migrating from OpenAI

If you were using OpenAI:

### Step 1: Get Gemini Key

Follow the guide above to get your free key.

### Step 2: Update Configuration

1. Open Settings in app
2. Replace OpenAI key with Gemini key
3. Save

### Step 3: Clear Cache (Optional)

If you want to regenerate solutions with Gemini:
```bash
rm ~/.interviewassistant/database.db
# Restart the app
```

### Step 4: Test

Generate a new solution to verify it works!

---

## Solution Quality

### What to expect from Gemini

✅ **Strengths**:
- Clean and well-structured code
- Clear explanations
- Good understanding of algorithms
- Accurate complexity analysis
- Support for multiple languages
- Excellent screenshot analysis
- Extracts problems from images accurately

⚠️ **Occasionally**:
- May be more verbose than GPT
- Sometimes includes extra comments
- May need additional parsing

### Quality Examples

**Problem**: Two Sum
- ✅ Optimal solution (O(n))
- ✅ Clear explanation
- ✅ Edge cases considered
- ✅ Ready-to-use code

**Problem**: Binary Search
- ✅ Correct implementation
- ✅ Correct complexity
- ✅ Well-defined base cases

**Screenshot Analysis**:
- ✅ Accurately extracts problem description
- ✅ Generates correct solution
- ✅ Works with various screenshot formats

---

## FAQ

### Q: Do I need a credit card?
**A**: ❌ No! Gemini is completely free.

### Q: Is there a time limit?
**A**: ❌ No! Use it forever for free.

### Q: How many solutions can I generate?
**A**: ✅ 1,500 per day, every day.

### Q: Is it better than GPT?
**A**: ✅ For free usage, yes! Quality similar to GPT-4.

### Q: Can I use it for real interviews?
**A**: ⚠️ Use responsibly. Check interview platform terms.

### Q: Which Gemini model is used?
**A**: `gemini-2.5-flash` - Latest and fastest model, completely free.

### Q: What if I want to use OpenAI?
**A**: You can! Edit `AppModule.kt` and uncomment the OpenAI line.

### Q: Does Gemini work offline?
**A**: ❌ No, needs internet. But cached solutions work offline.

### Q: Can I use both (Gemini and OpenAI)?
**A**: Currently only one at a time. Choose in code.

### Q: Does screenshot analysis work?
**A**: ✅ Yes! Only with Gemini (has vision support).

### Q: What image formats are supported?
**A**: PNG (automatically captured by the app).

---

## Useful Links

- **Get API Key**: https://makersuite.google.com/app/apikey
- **Gemini Documentation**: https://ai.google.dev/docs
- **Limits and Quotas**: https://ai.google.dev/pricing
- **Google AI Studio**: https://makersuite.google.com/

---

## Summary

✅ **Gemini is the best option for free usage**:
- No cost
- No credit card
- Generous limits
- Excellent quality
- Fast setup
- Vision support for screenshots

✅ **Setup in 2 minutes**:
1. Get key at https://makersuite.google.com/app/apikey
2. Paste in app
3. Start using!

✅ **1,500 solutions per day**:
- More than enough
- Resets daily
- Use freely!

✅ **Screenshot Analysis**:
- Capture coding challenges
- Instant AI analysis
- Perfect for practice

---

**Current Model**: Gemini 2.5 Flash
**API Version**: v1beta
**Cost**: 💰 Free
**Daily Limit**: 1,500 requests
**Requires Card**: ❌ No
**Vision Support**: ✅ Yes

**Start using now!** 🚀
