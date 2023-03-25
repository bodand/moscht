package hu.kszi2.moscht.parsing

import hu.kszi2.moscht.MachineType
import org.intellij.lang.annotations.Language
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import java.lang.Exception

class SyncJsonParserTest {
    private var parser = DefaultJsonParser()

    @Before
    fun initNewParser() {
        parser = DefaultJsonParser()
    }

    @Test
    fun `Json parser returns empty list from empty json list`() {
        val res = parser.parse("[]")

        assertTrue(res.isEmpty())
    }

    @Test
    fun `Json parser throws for parsing objects`() {
        assertThrows("{}", Exception::class.java) {
            parser.parse("{}")
        }
    }

    @Test
    fun `Json parser throws for parsing the empty string`() {
        assertThrows(Exception::class.java) {
            parser.parse("")
        }
    }

    @Test
    fun `Json parser returns empty list for levels without machines`() {
        @Language("JSON") val res = parser.parse(
            """
            [
              {
                "id": 2,
                "machines": [
                ],
                "last_query_time": "2023-03-19T10:36:04.650610Z"
              },
              {
                "id": 3,
                "machines": [
                ],
                "last_query_time": "2023-02-08T09:23:08.399981Z"
              },
              {
                "id": 5,
                "machines": [
                ],
                "last_query_time": "2022-04-15T21:13:03.590772Z"
              },
              {
                "id": 6,
                "machines": [
                ],
                "last_query_time": "2021-07-21T15:58:08.267971Z"
              },
              {
                "id": 7,
                "machines": [
                ],
                "last_query_time": "2023-03-19T10:36:05.583479Z"
              },
              {
                "id": 9,
                "machines": [
                ],
                "last_query_time": "2022-10-02T19:42:53.548361Z"
              },
              {
                "id": 10,
                "machines": [
                ],
                "last_query_time": "2023-03-19T10:36:05.332395Z"
              },
              {
                "id": 11,
                "machines": [
                ],
                "last_query_time": "2023-03-19T10:36:04.961297Z"
              },
              {
                "id": 13,
                "machines": [
                ],
                "last_query_time": "2023-03-19T10:36:05.737734Z"
              },
              {
                "id": 15,
                "machines": [
                ],
                "last_query_time": "2023-03-19T10:36:05.569010Z"
              },
              {
                "id": 17,
                "machines": [
                ],
                "last_query_time": "2023-03-19T10:36:05.759315Z"
              }
            ]
        """.trimIndent()
        )

        assertTrue(res.isEmpty())
    }

    @Test
    fun `Json parser returns correct machine data with only washing machine`() {
        @Language("JSON") val res = parser.parse(
            """
            [
              {
                "id": 2,
                "machines": [
                  {
                    "id": 21,
                    "kind_of": "WM",
                    "status": 0,
                    "message": null
                  }
                ],
                "last_query_time": "2023-03-19T14:08:04.814475Z"
              }
            ]
        """.trimIndent()
        )

        assertTrue(res.isNotEmpty())
        assertEquals(res[0].level, 2)
        assertEquals(res[0].type, MachineType.WashingMachine)
    }
}