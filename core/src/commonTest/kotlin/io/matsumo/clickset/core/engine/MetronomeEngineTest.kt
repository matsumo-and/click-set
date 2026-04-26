package io.matsumo.clickset.core.engine

import io.matsumo.clickset.core.infrastructure.engine.MetronomeEngineImpl
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MetronomeEngineTest {
    private val engine = MetronomeEngineImpl()

    @Test
    fun beatNumberCyclesFromOneToBeatsPerMeasure() =
        runTest {
            val beatsPerMeasure = 4
            val events =
                engine.tick(bpm = 120, beatsPerMeasure = beatsPerMeasure)
                    .take(beatsPerMeasure * 2)
                    .toList()

            val beatNumbers = events.map { it.beatNumber }
            assertEquals(listOf(1, 2, 3, 4, 1, 2, 3, 4), beatNumbers)
        }

    @Test
    fun isDownbeatOnlyOnBeatOne() =
        runTest {
            val events =
                engine.tick(bpm = 120, beatsPerMeasure = 4)
                    .take(4)
                    .toList()

            assertTrue(events[0].isDownbeat)
            assertFalse(events[1].isDownbeat)
            assertFalse(events[2].isDownbeat)
            assertFalse(events[3].isDownbeat)
        }

    @Test
    fun scheduledTimeNanosIsMonotonicallyIncreasing() =
        runTest {
            val events =
                engine.tick(bpm = 120, beatsPerMeasure = 4)
                    .take(8)
                    .toList()

            for (i in 1 until events.size) {
                assertTrue(events[i].scheduledTimeNanos > events[i - 1].scheduledTimeNanos)
            }
        }

    @Test
    fun scheduledTimeNanosMatchesExpectedInterval() =
        runTest {
            val bpm = 120
            val expectedIntervalNanos = 60_000_000_000L / bpm

            val events =
                engine.tick(bpm = bpm, beatsPerMeasure = 4)
                    .take(4)
                    .toList()

            for (i in 1 until events.size) {
                val actualInterval = events[i].scheduledTimeNanos - events[i - 1].scheduledTimeNanos
                assertEquals(expectedIntervalNanos, actualInterval)
            }
        }

    @Test
    fun bpmAndBeatsPerMeasureArePassedThrough() =
        runTest {
            val events =
                engine.tick(bpm = 90, beatsPerMeasure = 3)
                    .take(3)
                    .toList()

            events.forEach { event ->
                assertEquals(90, event.bpm)
                assertEquals(3, event.beatsPerMeasure)
            }
        }
}
