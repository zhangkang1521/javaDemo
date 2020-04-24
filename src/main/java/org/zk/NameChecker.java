package org.zk;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner6;
import javax.tools.Diagnostic;

public class NameChecker {
	
	private final Messager messager;
	
	NameCheckScanner nameCheckScanner = new NameCheckScanner();
	
	NameChecker(ProcessingEnvironment environment) {
		this.messager = environment.getMessager();
	}

	public void check(Element element) {
		nameCheckScanner.scan(element);
	}

	private class NameCheckScanner extends ElementScanner6<Void, Void> {

		@Override
		public Void visitVariable(VariableElement e, Void aVoid) {
			messager.printMessage(Diagnostic.Kind.WARNING, "check you variable name:" + e.getSimpleName());
			return null;
		}
	}
}
