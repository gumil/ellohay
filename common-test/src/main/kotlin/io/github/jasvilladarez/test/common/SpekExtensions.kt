/*
 * MIT License
 *
 * Copyright (c) 2018 Jasmine Villadarez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.jasvilladarez.test.common

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.jetbrains.spek.api.dsl.Spec

open class SpekRule(
        val start: () -> Unit,
        val finish: () -> Unit
)

fun Spec.addGroupRule(spekRule: SpekRule) {
//    beforeGroup { spekRule.start.invoke() }
    spekRule.start.invoke()
    afterGroup { spekRule.finish.invoke() }
}

fun Spec.addGroupRules(vararg spekRules: SpekRule) {
    spekRules.forEach {
        addGroupRule(it)
    }
}

fun Spec.addEachTestRule(spekRule: SpekRule) {
    beforeEachTest { spekRule.start.invoke() }
    afterEachTest { spekRule.finish.invoke() }
}

class RxSpekRule : SpekRule({
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
}, {
    RxAndroidPlugins.reset()
})