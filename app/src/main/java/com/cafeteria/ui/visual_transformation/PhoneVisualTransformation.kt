package com.cafeteria.ui.visual_transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.replace("-", "")
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 2 || i == 6) out += "-"
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 6) return offset + 1
                return offset + 2
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 8) return offset - 1
                return offset - 2
            }
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}