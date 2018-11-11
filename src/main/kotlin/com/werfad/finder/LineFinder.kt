package com.werfad.finder

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.werfad.KeyTagsGenerator
import com.werfad.Mark
import com.werfad.utils.findAllRegex


class LineFinder(val mode: Mode) : Finder {
    companion object {
        enum class Mode {
            ALL, ABOVE, BELOW
        }
    }

    override fun start(e: Editor, s: String, visibleRange: TextRange): List<Mark>? {
        val offsets = s.findAllRegex("(?m)^")
                .map { it + visibleRange.startOffset }
                .filter {
                    when (this.mode) {
                        Mode.ALL -> true
                        Mode.ABOVE -> it - e.caretModel.offset < 0
                        Mode.BELOW -> it - e.caretModel.offset > 0
                    }
                }
                .sortedBy { Math.abs(it - e.caretModel.offset) }
        val tags = KeyTagsGenerator.createTagsTree(offsets.size)
        return offsets.mapIndexed { index, offset ->
            Mark(tags[index], offset)
        }
    }

    override fun input(e: Editor, c: Char, lastMarks: List<Mark>): List<Mark> {
        return matchInputAndCreateMarks(c, lastMarks)
    }
}