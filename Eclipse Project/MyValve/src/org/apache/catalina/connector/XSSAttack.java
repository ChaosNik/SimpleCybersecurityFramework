package org.apache.catalina.connector;
import org.apache.commons.text.StringEscapeUtils;

public class XSSAttack
{
	public static boolean isXSSAttack(String text)
	{
		String input = StringEscapeUtils.unescapeHtml4(text).toLowerCase().replace(" ", "");
		if
		(
			input.contains("script") &&
			input.contains("<")
		)
			return true;
		if
		(
			input.contains("fscommand") ||
			input.contains("onfocus") ||
			input.contains("onactivate") ||
			input.contains("onafterprint") ||
			input.contains("onafterupdate") ||
			input.contains("onbeforeactivate") ||
			input.contains("onbeforecopy") ||
			input.contains("onbeforecut") ||
			input.contains("onbegin") ||
			input.contains("onbounce") ||
			input.contains("onblur") ||
			input.contains("oncontextmenu") ||
			input.contains("oncopy") ||
			input.contains("oncut") ||
			input.contains("onchange") ||
			input.contains("onclick") ||
			input.contains("oncancel") ||
			input.contains("ondblclick") ||
			input.contains("onclose") ||
			input.contains("onsubmit") ||
			input.contains("onselect") ||
			input.contains("onmouseup") ||
			input.contains("onmousedown") ||
			input.contains("onkeydown") ||
			input.contains("onkeyup") ||
			input.contains("onkeypress") ||
			input.contains("onload") ||
			input.contains("onmouseenter") ||
			input.contains("onmouseleave") ||
			input.contains("onmouseover") ||
			input.contains("onerror") ||
			input.contains("eval")
		)
			return true;
		return false;
	}
}
