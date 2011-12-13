// Copyright 2011 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.hughes.android.dictionary.engine;

import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import com.hughes.util.raf.RAFSerializable;
import com.hughes.util.raf.RAFSerializer;

public class TextEntry extends AbstractEntry implements RAFSerializable<TextEntry> {
  
  final String text;
  
  public TextEntry(final RandomAccessFile raf) throws IOException {
    text = raf.readUTF();
  }
  @Override
  public void write(RandomAccessFile raf) throws IOException {
    raf.writeUTF(text);
  }
  
  static final RAFSerializer<TextEntry> SERIALIZER = new RAFSerializer<TextEntry>() {
    @Override
    public TextEntry read(RandomAccessFile raf) throws IOException {
      return new TextEntry(raf);
    }

    @Override
    public void write(RandomAccessFile raf, TextEntry t) throws IOException {
      t.write(raf);
    }
  };
  
  @Override
  public int addToDictionary(final Dictionary dictionary) {
    dictionary.textEntries.add(this);
    return dictionary.textEntries.size() - 1;
  }

  public static class Row extends RowBase {
    
    Row(final RandomAccessFile raf, final int thisRowIndex,
        final Index index) throws IOException {
      super(raf, thisRowIndex, index);
    }
    
    public TextEntry getEntry() {
      return index.dict.textEntries.get(referenceIndex);
    }
    
    @Override
    public void print(PrintStream out) {
      out.println("  " + getEntry().text);
    }

    @Override
    public String getRawText(boolean compact) {
      return getEntry().text;
    }
  }



}