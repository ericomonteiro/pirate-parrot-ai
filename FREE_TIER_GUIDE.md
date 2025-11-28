# Free Tier Guide - OpenAI API

## Overview

The Interview Assistant MVP now uses **GPT-3.5 Turbo** by default, which is compatible with OpenAI's free tier accounts.

---

## OpenAI Free Tier

### What's Included

When you sign up for OpenAI, you get:
- **$5 free credits** (for new accounts)
- Access to **GPT-3.5 Turbo** model
- Valid for **3 months** from account creation

### What's NOT Included (Paid Only)

- ❌ GPT-4 models (including gpt-4-turbo-preview)
- ❌ GPT-4 Vision
- ❌ DALL-E 3
- ❌ Higher rate limits

---

## Current Configuration

The app is now configured to use:
- **Model**: `gpt-3.5-turbo`
- **Cost**: ~$0.002 per solution (very cheap!)
- **Speed**: 3-5 seconds per solution
- **Quality**: Good for most coding problems

### Cost Breakdown

With $5 free credits:
- **~2,500 solutions** can be generated
- **Cost per solution**: $0.002
- **Perfect for**: Interview preparation and practice

---

## Getting Started with Free Tier

### 1. Create OpenAI Account

1. Go to https://platform.openai.com/signup
2. Sign up with email or Google/Microsoft account
3. Verify your email
4. You'll get $5 free credits automatically

### 2. Get Your API Key

1. Go to https://platform.openai.com/api-keys
2. Click "Create new secret key"
3. Give it a name (e.g., "Interview Assistant")
4. Copy the key (starts with `sk-`)
5. **Save it securely** - you won't see it again!

### 3. Configure the App

**Option 1 - In App**:
1. Launch Interview Assistant
2. Click Settings (⚙️)
3. Paste your API key
4. Click outside to save

**Option 2 - Environment Variable**:
```bash
export OPENAI_API_KEY="sk-your-key-here"
./gradlew :composeApp:run
```

### 4. Test It

1. Search for "Two Sum"
2. Click the problem
3. Select "Python"
4. Wait 3-5 seconds
5. View your solution!

---

## Monitoring Usage

### Check Your Credits

1. Go to https://platform.openai.com/usage
2. See how many credits you've used
3. View usage by day/model
4. Set up usage alerts

### Typical Usage

**Per Solution**:
- Input tokens: ~200 (problem description)
- Output tokens: ~500 (code + explanation)
- Cost: ~$0.002

**For Interview Prep** (50 problems):
- Total cost: ~$0.10
- Remaining credits: $4.90
- Solutions generated: 50

---

## Tips for Free Tier Users

### Maximize Your Credits

1. **Cache Solutions**: The app automatically caches solutions
   - First load: Uses API (costs credits)
   - Subsequent loads: Free (from cache)

2. **Pre-generate Common Problems**: 
   - Generate solutions for top 50 LeetCode problems
   - Store them in cache
   - Use cached solutions during actual interviews

3. **Use Different Languages Wisely**:
   - Each language generates a new solution (costs credits)
   - Stick to 1-2 languages you actually use

4. **Test Before Interviews**:
   - Generate solutions for practice problems
   - Verify everything works
   - Don't waste credits during actual interviews

### Avoid Wasting Credits

❌ **Don't**:
- Generate the same solution multiple times
- Switch languages unnecessarily
- Test with random problems
- Leave the app running idle (it won't use credits)

✅ **Do**:
- Use cached solutions when possible
- Plan which problems to generate
- Monitor your usage regularly
- Set up billing alerts

---

## Upgrading (Optional)

### When to Upgrade

Consider upgrading if you:
- Run out of free credits
- Need GPT-4 for better solutions
- Want faster generation
- Need higher rate limits

### Paid Plans

**Pay-as-you-go**:
- No monthly fee
- Pay only for what you use
- GPT-3.5 Turbo: $0.002 per solution
- GPT-4: $0.03 per solution

**How to Upgrade**:
1. Go to https://platform.openai.com/account/billing
2. Add payment method
3. Add credits or enable auto-recharge
4. Continue using the app (no code changes needed)

---

## Model Comparison

### GPT-3.5 Turbo (Current - Free Tier)

**Pros**:
- ✅ Free tier compatible
- ✅ Very fast (3-5 seconds)
- ✅ Very cheap ($0.002/solution)
- ✅ Good for most problems

**Cons**:
- ⚠️ Less detailed explanations
- ⚠️ May struggle with very hard problems
- ⚠️ Shorter code comments

**Best For**:
- Easy and Medium problems
- Interview preparation
- Learning algorithms
- Practice sessions

### GPT-4 (Paid Only)

**Pros**:
- ✅ Better code quality
- ✅ More detailed explanations
- ✅ Handles complex problems better
- ✅ Better edge case handling

**Cons**:
- ❌ Requires paid account
- ❌ More expensive ($0.03/solution)
- ❌ Slower (10-15 seconds)

**Best For**:
- Hard problems
- Production code
- Learning complex algorithms
- When quality matters most

---

## Troubleshooting Free Tier Issues

### "Model not found" Error

**Error**: `The model 'gpt-4-turbo-preview' does not exist or you do not have access to it`

**Solution**: ✅ Already fixed! The app now uses `gpt-3.5-turbo` by default.

### "Insufficient credits" Error

**Error**: `You exceeded your current quota`

**Solutions**:
1. Check usage: https://platform.openai.com/usage
2. Wait for credits to reset (if rate limited)
3. Add payment method to continue
4. Use cached solutions

### "Rate limit exceeded" Error

**Error**: `Rate limit reached for requests`

**Solutions**:
1. Wait 1 minute and try again
2. Free tier has lower rate limits
3. Upgrade for higher limits
4. Use cached solutions

---

## FAQ

### Q: How long do free credits last?
**A**: 3 months from account creation.

### Q: Can I get more free credits?
**A**: No, but you can create a new account (against ToS) or upgrade.

### Q: Will the app work after credits expire?
**A**: You'll need to add payment method, but the app will continue working.

### Q: Can I switch to GPT-4 later?
**A**: Yes! Just upgrade your account and change the model in the code.

### Q: How do I change the model?
**A**: Edit `AIService.kt` and change `model = "gpt-3.5-turbo"` to `model = "gpt-4"`.

### Q: Is GPT-3.5 good enough?
**A**: Yes! For most interview problems, GPT-3.5 Turbo works great.

---

## Cost Examples

### Scenario 1: Interview Prep (50 problems)
- Generate 50 solutions
- Cost: $0.10
- Remaining: $4.90
- **Result**: Plenty of credits left

### Scenario 2: Heavy Practice (500 problems)
- Generate 500 solutions
- Cost: $1.00
- Remaining: $4.00
- **Result**: Still good!

### Scenario 3: Daily Practice (30 days)
- 10 problems per day
- 300 solutions total
- Cost: $0.60
- **Result**: Excellent value

---

## Recommendations

### For Students
- ✅ Free tier is perfect
- ✅ $5 credits last months
- ✅ Focus on top 100 problems
- ✅ Use caching effectively

### For Job Seekers
- ✅ Free tier works great
- ✅ Pre-generate common problems
- ✅ Cache solutions before interviews
- ✅ Consider upgrading if needed

### For Practice
- ✅ Free tier is ideal
- ✅ Generate solutions as needed
- ✅ Review and learn
- ✅ No rush to upgrade

---

## Summary

✅ **Free Tier is Sufficient**:
- $5 free credits
- ~2,500 solutions
- Perfect for interview prep
- GPT-3.5 Turbo works great

✅ **App is Optimized**:
- Uses cheapest model
- Caches solutions
- Minimizes API calls
- Maximizes your credits

✅ **You're Ready**:
- Set up your API key
- Start generating solutions
- Practice for interviews
- Upgrade only if needed

---

**Current Model**: GPT-3.5 Turbo
**Free Tier Compatible**: ✅ Yes
**Cost per Solution**: $0.002
**Your Free Credits**: $5.00

**Start using the app now!** 🚀
