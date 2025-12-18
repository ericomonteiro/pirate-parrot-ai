package com.github.ericomonteiro.pirateparrotai.i18n

object SpanishStrings : StringResources {
    // App General
    override val appName = "Pirate-Parrot"
    override val settings = "Configuración"
    override val close = "Cerrar"
    override val save = "Guardar"
    override val cancel = "Cancelar"
    override val copy = "Copiar"
    override val clear = "Limpiar"
    override val reload = "Recargar"
    override val tips = "Consejos"
    
    // Home Screen
    override val homeWhatIs = "¿Qué es Pirate-Parrot?"
    override val homeWhatIsTitle = "¿Qué es Pirate-Parrot?"
    override val homeWhatIsContent = """
        Pirate-Parrot es una aplicación de escritorio diseñada para ayudarte durante entrevistas de código y exámenes de certificación.
        
        Captura tu pantalla, analiza el contenido usando la IA Gemini de Google y proporciona:
        
        • Soluciones de código con explicaciones para desafíos de programación
        • Respuestas correctas con explicaciones detalladas para preguntas de certificación
        • Análisis de complejidad de tiempo y espacio
        • Consejos de examen y servicios relacionados
        
        La app funciona en "modo sigiloso" - es invisible para software de compartir y grabar pantalla.
    """.trimIndent()
    override val homeConfigTitle = "Lo Que Necesitas Configurar"
    override val homeConfigContent = """
        Antes de usar la app, necesitas configurar:
        
        1. Clave de API Gemini (Requerido)
           • Ve a Google AI Studio (aistudio.google.com)
           • Crea una clave de API gratuita
           • Pégala en Configuración -> Clave de API
        
        2. Seleccionar Modelo de IA (Opcional)
           • Por defecto: gemini-2.5-flash (recomendado)
           • Para problemas complejos: gemini-2.5-pro
        
        3. Lenguaje Por Defecto (Opcional)
           • Elige tu lenguaje de programación preferido
        
        4. Modo Sigiloso (Activado por defecto)
           • Oculta la app de la captura de pantalla
    """.trimIndent()
    override val homeOpenSettings = "Abrir Configuración"
    override val homeHowToUseTitle = "Cómo Usar"
    override val homeHowToUseContentTemplate = """
        La app tiene dos modos principales:
        
        Desafíos de Código
        • Navega a un problema de código (LeetCode, HackerRank, etc.)
        • Presiona {captureShortcut} o haz clic en "Capturar"
        • La IA analizará y proporcionará una solución
        
        Certificación AWS
        • Abre tu pregunta del examen de certificación
        • Selecciona el tipo de certificación
        • Presiona {captureShortcut} o haz clic en "Capturar"
        • Obtén la respuesta correcta con explicaciones
        
        Atajos de Teclado
        • {captureShortcut} -> Capturar y Analizar
        • {stealthShortcut} -> Alternar Modo Sigiloso
        
        Consejos
        • Asegúrate de que la pregunta sea completamente visible
        • Revisa el Historial de Capturas para solución de problemas
    """.trimIndent()
    override val homeGetStarted = "Comenzar"
    override val homeCodeChallenge = "Desafío de Código"
    override val homeCodeChallengeDesc = "Resolver problemas de programación"
    override val homeCertification = "Certificación"
    override val homeCertificationDesc = "Preguntas de exámenes AWS"
    override val homeGenericExam = "Examen Genérico"
    override val homeGenericExamDesc = "ENEM, Vestibular, Concursos"
    override val homeYourAiCompanion = "Tu compañero de estudios con IA"
    
    // Settings Screen
    override val settingsGeminiApiKey = "Clave de API Gemini"
    override val settingsApiKeyPlaceholder = "sk-..."
    override val settingsGetApiKey = "Obtén tu clave de API en"
    override val settingsAiModel = "Modelo de IA"
    override val settingsDefaultLanguage = "Lenguaje Por Defecto"
    override val settingsDefaultLanguageHint = "Este lenguaje será preseleccionado al analizar desafíos"
    override val settingsStealthFeatures = "Funciones Sigilosas"
    override val settingsHideFromCapture = "Ocultar de Captura de Pantalla"
    override val settingsHideFromCaptureDesc = "Hace la ventana invisible en Zoom, Meet, Teams"
    override val settingsCaptureRegion = "Región de Captura"
    override val settingsUseCustomRegion = "Usar Región Personalizada"
    override val settingsCaptureEntireScreen = "Capturar pantalla completa"
    override val settingsRegionInfo = "Región: %dx%d en (%d, %d)"
    override val settingsSelectRegion = "Seleccionar Región"
    override val settingsDefineRegionHint = "Define un área específica de la pantalla para capturar en lugar de la pantalla completa"
    override val settingsTroubleshooting = "Solución de Problemas"
    override val settingsScreenshotHistory = "Historial de Capturas"
    override val settingsScreenshotHistoryDesc = "Ver capturas de pantalla y resultados de análisis para depuración"
    override val settingsTestApiConnection = "Probar Conexión de API"
    override val settingsTipsContentTemplate = """
        Atajos Globales (funcionan incluso cuando la app no está enfocada):
        • {captureShortcut} - Capturar pantalla y analizar
        • {stealthShortcut} - Alternar modo sigiloso

        • ¡El código de la solución se copia automáticamente!
        • Gemini es completamente GRATIS (no necesita tarjeta de crédito)
    """.trimIndent()
    override val settingsAppLanguage = "Idioma de la App"
    override val settingsAppLanguageHint = "Selecciona el idioma de la interfaz de la aplicación"
    override val settingsSystemDefault = "Por Defecto del Sistema"
    
    // Screenshot Analysis
    override val screenshotCapture = "Capturar"
    override val screenshotAnalyzing = "Analizando..."
    override val screenshotNoImage = "Ninguna imagen capturada"
    override val screenshotCaptureHintTemplate = "Presiona {captureShortcut} o haz clic en Capturar"
    override val screenshotSolution = "Solución"
    override val screenshotCopied = "¡Copiado!"
    override val screenshotCopyCode = "Copiar Código"
    override val screenshotSelectLanguage = "Seleccionar Lenguaje"
    
    // Certification Analysis
    override val certificationTitle = "Análisis de Certificación"
    override val certificationSelectType = "Seleccionar Tipo de Certificación"
    override val certificationAnswer = "Respuesta"
    override val certificationExplanation = "Explicación"
    override val certificationRelatedServices = "Servicios Relacionados"
    override val certificationExamTips = "Consejos del Examen"
    
    // Generic Exam
    override val genericExamTitle = "Examen Genérico"
    override val genericExamSelectType = "Seleccionar Tipo de Examen"
    override val genericExamAnswer = "Respuesta"
    
    // History
    override val historyTitle = "Historial de Capturas"
    override val historyEmpty = "Ninguna captura de pantalla aún"
    override val historyDelete = "Eliminar"
    override val historyViewDetails = "Ver Detalles"
    
    // Errors
    override val errorApiKeyNotSet = "Error: Clave de API no configurada"
    override val errorAnalysisFailed = "Falló el análisis"
    override val errorCaptureFailed = "Falló la captura de pantalla"
    override val errorNetworkFailed = "Error de red"
    
    // Code Challenge
    override val codeChallengeTitle = "Desafío de Código"
    override val codeChallengeProblem = "Problema"
    override val codeChallengeSolution = "Solución"
    override val codeChallengeComplexity = "Complejidad"
    override val codeChallengeTimeComplexity = "Complejidad de Tiempo"
    override val codeChallengeSpaceComplexity = "Complejidad de Espacio"
}
