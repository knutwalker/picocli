/*
   Copyright 2017 Remko Popma

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package picocli.examples.model;

import picocli.CommandLine;
import picocli.CommandLine.AbstractSimpleParseResultHandler;
import picocli.CommandLine.DefaultExceptionHandler;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.Model.PositionalParamSpec;
import picocli.CommandLine.ParseResult;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class SimpleResultHandlerWithoutReturnValue {
    public static void main(final String[] args) {

        CommandSpec spec = CommandSpec.create();
        spec.mixinStandardHelpOptions(true);
        spec.add(OptionSpec.builder("-c", "--count")
                .paramLabel("COUNT")
                .type(int.class)
                .description("number of times to execute").build());
        spec.add(PositionalParamSpec.builder()
                .paramLabel("FILES")
                .type(List.class)
                .auxiliaryTypes(File.class)
                .description("The files to process").build());
        CommandLine commandLine = new CommandLine(spec);

        commandLine.parseWithSimpleHandlers(new AbstractSimpleParseResultHandler() {
            public void handle(ParseResult pr) {
                int count = pr.optionValue('c', 1);
                List<File> files = pr.positionalValue(0, Collections.<File>emptyList());
                for (int i = 0; i < count; i++) {
                    for (File f : files) {
                        System.out.printf("%d: %s%n", i, f);
                    }
                }
            }
        }.useOut(System.out).andExit(123), new DefaultExceptionHandler<Void>().andExit(567), args);
    }
}