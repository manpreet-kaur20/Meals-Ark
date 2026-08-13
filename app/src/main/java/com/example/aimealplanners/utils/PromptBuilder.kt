package com.example.aimealplanners.utils

/**
 * Utility to build ChatGPT-friendly meal generation prompts
 * from user preferences collected during onboarding.
 */
object PromptBuilder {

    fun buildDailyMealPrompt(
        goals: List<String> = emptyList(),
        dietaryApproach: String = "No specific diet",
        allergies: List<String> = emptyList(),
        cuisines: List<String> = emptyList(),
        calorieGoal: Int = 2000,
        budget: String = "Medium",
        mealVariety: String = "Medium",
        sameLunchDinner: Boolean = false,
        specialRequests: String = ""
    ): String {
        val sb = StringBuilder()

        sb.appendLine("Generate a complete daily meal plan with breakfast, lunch, dinner, and snacks.")
        sb.appendLine()

        // Calorie target
        sb.appendLine("Daily calorie target: $calorieGoal calories.")

        // Goals
        if (goals.isNotEmpty()) {
            sb.appendLine("Health goals: ${goals.joinToString(", ")}.")
        }

        // Diet
        if (dietaryApproach != "No specific diet") {
            sb.appendLine("Dietary approach: $dietaryApproach.")
        }

        // Allergies
        if (allergies.isNotEmpty()) {
            sb.appendLine("Food allergies/restrictions (MUST avoid): ${allergies.joinToString(", ")}.")
        }

        // Cuisines
        if (cuisines.isNotEmpty()) {
            sb.appendLine("Preferred cuisines: ${cuisines.joinToString(", ")}.")
        }

        // Budget
        sb.appendLine("Budget level: $budget.")

        // Variety
        sb.appendLine("Meal variety preference: $mealVariety.")

        // Same lunch/dinner
        if (sameLunchDinner) {
            sb.appendLine("The user prefers the same meal for lunch and dinner (batch cooking).")
        }

        // Special requests
        if (specialRequests.isNotBlank()) {
            sb.appendLine("Special requests: $specialRequests")
        }

        sb.appendLine()
        sb.appendLine("For each meal, provide: meal name, description, calories, protein (g), carbs (g), fat (g), and a simple recipe with ingredients and steps.")
        sb.appendLine("Format the response as JSON.")

        return sb.toString()
    }

    fun buildMealRegenerationPrompt(
        mealType: String,
        reason: String = "",
        calorieGoal: Int = 500,
        dietaryApproach: String = "No specific diet",
        allergies: List<String> = emptyList()
    ): String {
        val sb = StringBuilder()

        sb.appendLine("Regenerate a single $mealType meal suggestion.")
        sb.appendLine("Target calories for this meal: ~$calorieGoal calories.")

        if (dietaryApproach != "No specific diet") {
            sb.appendLine("Dietary approach: $dietaryApproach.")
        }

        if (allergies.isNotEmpty()) {
            sb.appendLine("Must avoid: ${allergies.joinToString(", ")}.")
        }

        if (reason.isNotBlank()) {
            sb.appendLine("Reason for regeneration: $reason")
        }

        sb.appendLine()
        sb.appendLine("Provide: meal name, description, calories, protein (g), carbs (g), fat (g), and a simple recipe.")
        sb.appendLine("Format the response as JSON.")

        return sb.toString()
    }
}
