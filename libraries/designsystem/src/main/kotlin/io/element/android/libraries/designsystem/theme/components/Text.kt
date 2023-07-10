/*
 * Copyright (c) 2023 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.element.android.libraries.designsystem.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import io.element.android.libraries.designsystem.preview.ElementPreviewDark
import io.element.android.libraries.designsystem.preview.ElementPreviewLight
import io.element.android.libraries.designsystem.preview.PreviewGroup
import io.element.android.libraries.theme.utils.toHrf
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    // Will be removed, only style should be used
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    // Will be removed, only style should be used
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    // Will be removed, only style should be used
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        minLines = minLines,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style,
    )
}

@Composable
fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    // Will be removed, only style should be used
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    // Will be removed, only style should be used
    fontWeight: FontWeight? = null,
    // Will be removed, only style should be used
    fontFamily: FontFamily? = null,
    // Will be removed, only style should be used
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    // Will be removed, only style should be used
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: ImmutableMap<String, InlineTextContent> = persistentMapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        minLines = minLines,
        maxLines = maxLines,
        inlineContent = inlineContent,
        onTextLayout = onTextLayout,
        style = style,
    )
}

@Preview(group = PreviewGroup.Text)
@Composable
internal fun TextLightPreview() = ElementPreviewLight { ContentToPreview() }

@Preview(group = PreviewGroup.Text)
@Composable
internal fun TextDarkPreview() = ElementPreviewDark { ContentToPreview() }

@Composable
private fun ContentToPreview() {
    val colors = mapOf(
        "primary" to MaterialTheme.colorScheme.primary,
        "secondary" to MaterialTheme.colorScheme.secondary,
        "tertiary" to MaterialTheme.colorScheme.tertiary,
        "background" to MaterialTheme.colorScheme.background,
        "error" to MaterialTheme.colorScheme.error,
        "surface" to MaterialTheme.colorScheme.surface,
        "surfaceVariant" to MaterialTheme.colorScheme.surfaceVariant,
        "primaryContainer" to MaterialTheme.colorScheme.primaryContainer,
        "secondaryContainer" to MaterialTheme.colorScheme.secondaryContainer,
        "tertiaryContainer" to MaterialTheme.colorScheme.tertiaryContainer,
        // "inversePrimary" to MaterialTheme.colorScheme.inversePrimary,
        "errorContainer" to MaterialTheme.colorScheme.errorContainer,
        "inverseSurface" to MaterialTheme.colorScheme.inverseSurface,
    )
    Column(
        modifier = Modifier.width(IntrinsicSize.Max)
    ) {
        colors.keys.forEach { name ->
            val color = colors[name]!!
            val textColor = contentColorFor(backgroundColor = color)
            Box(
                modifier = Modifier
                    .background(color = color)
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Text(
                    text = "Text on $name\n${textColor.toHrf()} on ${color.toHrf()}",
                    color = textColor,
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}
