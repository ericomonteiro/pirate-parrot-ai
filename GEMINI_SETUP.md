# Guia de Configuração - Google Gemini (GRÁTIS!)

## 🎉 Boa Notícia!

O aplicativo agora usa **Google Gemini** por padrão, que é **completamente gratuito** e não requer cartão de crédito!

---

## Por que Gemini?

### ✅ Vantagens

1. **Completamente Gratuito**
   - Sem necessidade de cartão de crédito
   - Sem limite de créditos
   - Sem expiração

2. **Limites Generosos**
   - 60 requisições por minuto
   - 1.500 requisições por dia
   - Suficiente para prática intensiva

3. **Qualidade Excelente**
   - Comparável ao GPT-4
   - Ótimo para problemas de programação
   - Explicações claras e detalhadas

4. **Rápido**
   - 3-5 segundos por solução
   - Respostas consistentes
   - Baixa latência

### ❌ OpenAI (Não Recomendado)

- Requer cartão de crédito
- Sem tier gratuito real
- Custa $0.002 por solução
- Quota limitada

---

## Como Configurar o Gemini

### Passo 1: Obter API Key (2 minutos)

1. **Acesse**: https://makersuite.google.com/app/apikey
   
2. **Faça login** com sua conta Google
   - Qualquer conta Google funciona
   - Não precisa de conta especial

3. **Clique em "Create API Key"**
   - Escolha "Create API key in new project"
   - Ou selecione um projeto existente

4. **Copie a chave**
   - Começa com algo como `AIza...`
   - Salve em local seguro
   - Você pode ver novamente depois

### Passo 2: Configurar no App

**Opção 1 - Interface do App**:
1. Abra o Interview Assistant
2. Clique em Settings (⚙️)
3. Cole sua API key do Gemini
4. Pronto!

**Opção 2 - Variável de Ambiente**:
```bash
export OPENAI_API_KEY="sua-chave-gemini-aqui"
./gradlew :composeApp:run
```

> **Nota**: Mesmo sendo Gemini, usamos a mesma variável `OPENAI_API_KEY` para compatibilidade.

### Passo 3: Testar

1. Busque por "Two Sum"
2. Clique no problema
3. Selecione "Python"
4. Aguarde 3-5 segundos
5. ✅ Solução gerada!

---

## Comparação: Gemini vs OpenAI

| Característica | Google Gemini | OpenAI GPT-3.5 |
|----------------|---------------|----------------|
| **Custo** | ✅ Grátis | ❌ Pago |
| **Cartão de Crédito** | ✅ Não precisa | ❌ Obrigatório |
| **Requisições/dia** | ✅ 1.500 | ❌ Depende de créditos |
| **Qualidade** | ✅ Excelente | ✅ Boa |
| **Velocidade** | ✅ 3-5s | ✅ 3-5s |
| **Limite de tempo** | ✅ Sem limite | ❌ 3 meses |
| **Setup** | ✅ 2 minutos | ❌ 10 minutos |

**Vencedor**: 🏆 **Google Gemini**

---

## Limites do Tier Gratuito

### O que você pode fazer

✅ **1.500 requisições por dia**:
- 1.500 soluções por dia
- Mais que suficiente para prática
- Reseta todo dia

✅ **60 requisições por minuto**:
- Gere soluções rapidamente
- Sem espera entre requisições
- Perfeito para sessões de estudo

### Exemplo de Uso

**Preparação para Entrevista** (1 semana):
- 50 problemas por dia
- 350 problemas total
- ✅ Bem dentro do limite

**Prática Intensiva** (1 dia):
- 100 problemas
- ✅ Sem problemas

**Uso Normal**:
- 10-20 problemas por dia
- ✅ Perfeito

---

## Troubleshooting

### "API key not valid"

**Solução**:
1. Verifique se copiou a chave completa
2. Gere uma nova chave em https://makersuite.google.com/app/apikey
3. Certifique-se de que a API do Gemini está ativada

### "Quota exceeded"

**Causa**: Você excedeu 1.500 requisições no dia

**Solução**:
1. Aguarde até o próximo dia (reseta à meia-noite UTC)
2. Use soluções em cache (não conta no limite)
3. O limite é bem generoso, difícil de atingir

### "Rate limit exceeded"

**Causa**: Mais de 60 requisições por minuto

**Solução**:
1. Aguarde 1 minuto
2. Continue gerando soluções
3. Muito raro de acontecer no uso normal

### "Model not found"

**Solução**:
1. Certifique-se de que está usando a versão mais recente do app
2. O modelo usado é `gemini-pro`
3. Verifique se a API está ativada no Google Cloud Console

---

## Dicas para Maximizar o Uso

### 1. Use o Cache

- Soluções são automaticamente cacheadas
- Segunda visualização = instantânea
- Não conta no limite de requisições

### 2. Planeje suas Sessões

- Identifique os problemas que quer resolver
- Gere todas as soluções de uma vez
- Estude offline depois

### 3. Diferentes Linguagens

- Cada linguagem = nova requisição
- Escolha 1-2 linguagens principais
- Gere apenas o que vai usar

### 4. Monitore seu Uso

- Gemini não tem dashboard de uso
- Mas com 1.500/dia, é difícil exceder
- Use livremente!

---

## Migrando do OpenAI

Se você estava usando OpenAI:

### Passo 1: Obter Chave Gemini

Siga o guia acima para obter sua chave gratuita.

### Passo 2: Atualizar Configuração

1. Abra Settings no app
2. Substitua a chave OpenAI pela chave Gemini
3. Salve

### Passo 3: Limpar Cache (Opcional)

Se quiser regenerar soluções com Gemini:
```bash
rm ~/.interviewassistant/database.db
# Reinicie o app
```

### Passo 4: Testar

Gere uma nova solução para verificar que funciona!

---

## Qualidade das Soluções

### O que esperar do Gemini

✅ **Pontos Fortes**:
- Código limpo e bem estruturado
- Explicações claras
- Bom entendimento de algoritmos
- Análise de complexidade precisa
- Suporte a múltiplas linguagens

⚠️ **Ocasionalmente**:
- Pode ser mais verboso que GPT
- Às vezes inclui comentários extras
- Pode precisar de parsing adicional

### Exemplos de Qualidade

**Problema**: Two Sum
- ✅ Solução ótima (O(n))
- ✅ Explicação clara
- ✅ Edge cases considerados
- ✅ Código pronto para usar

**Problema**: Binary Search
- ✅ Implementação correta
- ✅ Complexidade correta
- ✅ Casos base bem definidos

---

## FAQ

### P: Preciso de cartão de crédito?
**R**: ❌ Não! Gemini é completamente gratuito.

### P: Tem limite de tempo?
**R**: ❌ Não! Use para sempre gratuitamente.

### P: Quantas soluções posso gerar?
**R**: ✅ 1.500 por dia, todos os dias.

### P: É melhor que GPT?
**R**: ✅ Para uso gratuito, sim! Qualidade similar ao GPT-4.

### P: Posso usar para entrevistas reais?
**R**: ⚠️ Use com responsabilidade. Verifique os termos da plataforma de entrevista.

### P: Qual modelo do Gemini é usado?
**R**: `gemini-1.5-flash` - O modelo mais recente e rápido, completamente gratuito.

### P: E se eu quiser usar OpenAI?
**R**: Você pode! Edite `AppModule.kt` e descomente a linha do OpenAI.

### P: Gemini funciona offline?
**R**: ❌ Não, precisa de internet. Mas soluções cacheadas funcionam offline.

### P: Posso usar ambos (Gemini e OpenAI)?
**R**: Atualmente apenas um por vez. Escolha no código.

---

## Links Úteis

- **Obter API Key**: https://makersuite.google.com/app/apikey
- **Documentação Gemini**: https://ai.google.dev/docs
- **Limites e Quotas**: https://ai.google.dev/pricing
- **Google AI Studio**: https://makersuite.google.com/

---

## Resumo

✅ **Gemini é a melhor opção para uso gratuito**:
- Sem custo
- Sem cartão de crédito
- Limites generosos
- Qualidade excelente
- Setup rápido

✅ **Configuração em 2 minutos**:
1. Obter chave em https://makersuite.google.com/app/apikey
2. Colar no app
3. Começar a usar!

✅ **1.500 soluções por dia**:
- Mais que suficiente
- Reseta diariamente
- Use à vontade!

---

**Modelo Atual**: Gemini 1.5 Flash
**Versão da API**: v1
**Custo**: 💰 Grátis
**Limite Diário**: 1.500 requisições
**Requer Cartão**: ❌ Não

**Comece a usar agora!** 🚀
