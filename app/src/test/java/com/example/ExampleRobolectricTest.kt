package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.GameDataProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Kata Nusantara", appName)
  }

  @Test
  fun `verify cases and suspects data loaded properly`() {
    val initialCases = GameDataProvider.getInitialCases()
    assertTrue(initialCases.isNotEmpty())
    val case1 = initialCases.first()
    assertEquals("case_01", case1.id)
    assertTrue(case1.suspects.isNotEmpty())
    assertTrue(case1.challenges.isNotEmpty())
    assertTrue(case1.clues.isNotEmpty())
    val culprit = case1.suspects.find { it.isCulprit }
    assertNotNull(culprit)
  }

  @Test
  fun `verify detective characters loaded with valid perks`() {
    val characters = GameDataProvider.characters
    assertEquals(3, characters.size)
    val arya = characters.find { it.id == "char_arya" }
    val kirana = characters.find { it.id == "char_kirana" }
    val panji = characters.find { it.id == "char_panji" }

    assertNotNull(arya)
    assertNotNull(kirana)
    assertNotNull(panji)

    assertTrue(arya!!.specialtyPerk.isNotEmpty())
    assertTrue(kirana!!.specialtyPerk.isNotEmpty())
    assertTrue(panji!!.specialtyPerk.isNotEmpty())
  }

  @Test
  fun `verify cases have expanded rich questions`() {
    val cases = GameDataProvider.getInitialCases()
    assertTrue(cases.size >= 3)
    cases.take(3).forEach { c ->
      assertTrue("Case ${c.id} should have at least 8 questions", c.challenges.size >= 8)
      c.challenges.forEach { q ->
        assertTrue(q.options.size >= 4)
        assertTrue(q.correctIndex in q.options.indices)
        assertTrue(q.explanation.isNotEmpty())
      }
    }
  }
}
