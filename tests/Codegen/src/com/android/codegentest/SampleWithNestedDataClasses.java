/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.codegentest;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.DataClass;

/**
 * An example of deeply nested data classes
 */
public class SampleWithNestedDataClasses {

    int mFoo = 0;

    @DataClass(genEqualsHashCode = true)
    public static class NestedDataClass implements Parcelable {

        @NonNull String mBar;



        // Code below generated by codegen v1.0.18.
        //
        // DO NOT MODIFY!
        // CHECKSTYLE:OFF Generated code
        //
        // To regenerate run:
        // $ codegen $ANDROID_BUILD_TOP/frameworks/base/tests/Codegen/src/com/android/codegentest/SampleWithNestedDataClasses.java
        //
        // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
        //   Settings > Editor > Code Style > Formatter Control
        //@formatter:off


        @DataClass.Generated.Member
        public NestedDataClass(
                @NonNull String bar) {
            this.mBar = bar;
            com.android.internal.util.AnnotationValidations.validate(
                    NonNull.class, null, mBar);

            // onConstructed(); // You can define this method to get a callback
        }

        @DataClass.Generated.Member
        public @NonNull String getBar() {
            return mBar;
        }

        @Override
        @DataClass.Generated.Member
        public boolean equals(@android.annotation.Nullable Object o) {
            // You can override field equality logic by defining either of the methods like:
            // boolean fieldNameEquals(NestedDataClass other) { ... }
            // boolean fieldNameEquals(FieldType otherValue) { ... }

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            @SuppressWarnings("unchecked")
            NestedDataClass that = (NestedDataClass) o;
            //noinspection PointlessBooleanExpression
            return true
                    && java.util.Objects.equals(mBar, that.mBar);
        }

        @Override
        @DataClass.Generated.Member
        public int hashCode() {
            // You can override field hashCode logic by defining methods like:
            // int fieldNameHashCode() { ... }

            int _hash = 1;
            _hash = 31 * _hash + java.util.Objects.hashCode(mBar);
            return _hash;
        }

        @Override
        @DataClass.Generated.Member
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            // You can override field parcelling by defining methods like:
            // void parcelFieldName(Parcel dest, int flags) { ... }

            dest.writeString(mBar);
        }

        @Override
        @DataClass.Generated.Member
        public int describeContents() { return 0; }

        /** @hide */
        @SuppressWarnings({"unchecked", "RedundantCast"})
        @DataClass.Generated.Member
        protected NestedDataClass(@NonNull Parcel in) {
            // You can override field unparcelling by defining methods like:
            // static FieldType unparcelFieldName(Parcel in) { ... }

            String bar = in.readString();

            this.mBar = bar;
            com.android.internal.util.AnnotationValidations.validate(
                    NonNull.class, null, mBar);

            // onConstructed(); // You can define this method to get a callback
        }

        @DataClass.Generated.Member
        public static final @NonNull Parcelable.Creator<NestedDataClass> CREATOR
                = new Parcelable.Creator<NestedDataClass>() {
            @Override
            public NestedDataClass[] newArray(int size) {
                return new NestedDataClass[size];
            }

            @Override
            public NestedDataClass createFromParcel(@NonNull Parcel in) {
                return new NestedDataClass(in);
            }
        };

        @DataClass.Generated(
                time = 1603836851627L,
                codegenVersion = "1.0.18",
                sourceFile = "frameworks/base/tests/Codegen/src/com/android/codegentest/SampleWithNestedDataClasses.java",
                inputSignatures = " @android.annotation.NonNull java.lang.String mBar\nclass NestedDataClass extends java.lang.Object implements [android.os.Parcelable]\n@com.android.internal.util.DataClass(genEqualsHashCode=true)")
        @Deprecated
        private void __metadata() {}


        //@formatter:on
        // End of generated code

    }

    @DataClass(genEqualsHashCode = true)
    public static class NestedDataClass2 implements Parcelable {

        @NonNull String mBaz;

        @DataClass(genEqualsHashCode = true)
        public static class NestedDataClass3 implements Parcelable {

            @NonNull long mBaz2;



            // Code below generated by codegen v1.0.18.
            //
            // DO NOT MODIFY!
            // CHECKSTYLE:OFF Generated code
            //
            // To regenerate run:
            // $ codegen $ANDROID_BUILD_TOP/frameworks/base/tests/Codegen/src/com/android/codegentest/SampleWithNestedDataClasses.java
            //
            // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
            //   Settings > Editor > Code Style > Formatter Control
            //@formatter:off


            @DataClass.Generated.Member
            public NestedDataClass3(
                    @NonNull long baz2) {
                this.mBaz2 = baz2;
                com.android.internal.util.AnnotationValidations.validate(
                        NonNull.class, null, mBaz2);

                // onConstructed(); // You can define this method to get a callback
            }

            @DataClass.Generated.Member
            public @NonNull long getBaz2() {
                return mBaz2;
            }

            @Override
            @DataClass.Generated.Member
            public boolean equals(@android.annotation.Nullable Object o) {
                // You can override field equality logic by defining either of the methods like:
                // boolean fieldNameEquals(NestedDataClass3 other) { ... }
                // boolean fieldNameEquals(FieldType otherValue) { ... }

                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                @SuppressWarnings("unchecked")
                NestedDataClass3 that = (NestedDataClass3) o;
                //noinspection PointlessBooleanExpression
                return true
                        && mBaz2 == that.mBaz2;
            }

            @Override
            @DataClass.Generated.Member
            public int hashCode() {
                // You can override field hashCode logic by defining methods like:
                // int fieldNameHashCode() { ... }

                int _hash = 1;
                _hash = 31 * _hash + Long.hashCode(mBaz2);
                return _hash;
            }

            @Override
            @DataClass.Generated.Member
            public void writeToParcel(@NonNull Parcel dest, int flags) {
                // You can override field parcelling by defining methods like:
                // void parcelFieldName(Parcel dest, int flags) { ... }

                dest.writeLong(mBaz2);
            }

            @Override
            @DataClass.Generated.Member
            public int describeContents() { return 0; }

            /** @hide */
            @SuppressWarnings({"unchecked", "RedundantCast"})
            @DataClass.Generated.Member
            protected NestedDataClass3(@NonNull Parcel in) {
                // You can override field unparcelling by defining methods like:
                // static FieldType unparcelFieldName(Parcel in) { ... }

                long baz2 = in.readLong();

                this.mBaz2 = baz2;
                com.android.internal.util.AnnotationValidations.validate(
                        NonNull.class, null, mBaz2);

                // onConstructed(); // You can define this method to get a callback
            }

            @DataClass.Generated.Member
            public static final @NonNull Parcelable.Creator<NestedDataClass3> CREATOR
                    = new Parcelable.Creator<NestedDataClass3>() {
                @Override
                public NestedDataClass3[] newArray(int size) {
                    return new NestedDataClass3[size];
                }

                @Override
                public NestedDataClass3 createFromParcel(@NonNull Parcel in) {
                    return new NestedDataClass3(in);
                }
            };

            @DataClass.Generated(
                    time = 1603836851635L,
                    codegenVersion = "1.0.18",
                    sourceFile = "frameworks/base/tests/Codegen/src/com/android/codegentest/SampleWithNestedDataClasses.java",
                    inputSignatures = " @android.annotation.NonNull long mBaz2\nclass NestedDataClass3 extends java.lang.Object implements [android.os.Parcelable]\n@com.android.internal.util.DataClass(genEqualsHashCode=true)")
            @Deprecated
            private void __metadata() {}


            //@formatter:on
            // End of generated code

        }



        // Code below generated by codegen v1.0.18.
        //
        // DO NOT MODIFY!
        // CHECKSTYLE:OFF Generated code
        //
        // To regenerate run:
        // $ codegen $ANDROID_BUILD_TOP/frameworks/base/tests/Codegen/src/com/android/codegentest/SampleWithNestedDataClasses.java
        //
        // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
        //   Settings > Editor > Code Style > Formatter Control
        //@formatter:off


        @DataClass.Generated.Member
        public NestedDataClass2(
                @NonNull String baz) {
            this.mBaz = baz;
            com.android.internal.util.AnnotationValidations.validate(
                    NonNull.class, null, mBaz);

            // onConstructed(); // You can define this method to get a callback
        }

        @DataClass.Generated.Member
        public @NonNull String getBaz() {
            return mBaz;
        }

        @Override
        @DataClass.Generated.Member
        public boolean equals(@android.annotation.Nullable Object o) {
            // You can override field equality logic by defining either of the methods like:
            // boolean fieldNameEquals(NestedDataClass2 other) { ... }
            // boolean fieldNameEquals(FieldType otherValue) { ... }

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            @SuppressWarnings("unchecked")
            NestedDataClass2 that = (NestedDataClass2) o;
            //noinspection PointlessBooleanExpression
            return true
                    && java.util.Objects.equals(mBaz, that.mBaz);
        }

        @Override
        @DataClass.Generated.Member
        public int hashCode() {
            // You can override field hashCode logic by defining methods like:
            // int fieldNameHashCode() { ... }

            int _hash = 1;
            _hash = 31 * _hash + java.util.Objects.hashCode(mBaz);
            return _hash;
        }

        @Override
        @DataClass.Generated.Member
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            // You can override field parcelling by defining methods like:
            // void parcelFieldName(Parcel dest, int flags) { ... }

            dest.writeString(mBaz);
        }

        @Override
        @DataClass.Generated.Member
        public int describeContents() { return 0; }

        /** @hide */
        @SuppressWarnings({"unchecked", "RedundantCast"})
        @DataClass.Generated.Member
        protected NestedDataClass2(@NonNull Parcel in) {
            // You can override field unparcelling by defining methods like:
            // static FieldType unparcelFieldName(Parcel in) { ... }

            String baz = in.readString();

            this.mBaz = baz;
            com.android.internal.util.AnnotationValidations.validate(
                    NonNull.class, null, mBaz);

            // onConstructed(); // You can define this method to get a callback
        }

        @DataClass.Generated.Member
        public static final @NonNull Parcelable.Creator<NestedDataClass2> CREATOR
                = new Parcelable.Creator<NestedDataClass2>() {
            @Override
            public NestedDataClass2[] newArray(int size) {
                return new NestedDataClass2[size];
            }

            @Override
            public NestedDataClass2 createFromParcel(@NonNull Parcel in) {
                return new NestedDataClass2(in);
            }
        };

        @DataClass.Generated(
                time = 1603836851640L,
                codegenVersion = "1.0.18",
                sourceFile = "frameworks/base/tests/Codegen/src/com/android/codegentest/SampleWithNestedDataClasses.java",
                inputSignatures = " @android.annotation.NonNull java.lang.String mBaz\nclass NestedDataClass2 extends java.lang.Object implements [android.os.Parcelable]\n@com.android.internal.util.DataClass(genEqualsHashCode=true)")
        @Deprecated
        private void __metadata() {}


        //@formatter:on
        // End of generated code

    }

    void someCode() {}
}
