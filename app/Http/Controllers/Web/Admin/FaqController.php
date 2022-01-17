<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\FaqDataTable;
use App\Http\Controllers\Controller;
use App\Models\faq;
use App\Http\Requests\Admin\Settings\Faq\StoreRequest;

class FaqController extends Controller
{
    public function index(FaqDataTable $dataTable)
    {
        return $dataTable->render('web.admin.settings.faq.index');
    }
    public function create()
    {
        return view('web.admin.settings.faq.create.create');
    }
    public function store(StoreRequest $request)
    {
        $faq = new faq();
        $faq->question_en = $request->input('question_en');
        $faq->question_ar = $request->input('question_ar');
        $faq->answer_en = $request->input('answer_en');
        $faq->answer_ar = $request->input('answer_ar');
        $faq->save();

        return redirect()->route('admin.settings.faq.index')->with('success', 'frequently asked question created successfully');
    }

    public function edit(faq $faq)
    {
        return view('web.admin.settings.faq.edit.edit', [
            'q'   =>  $faq
        ]);
    }
    public function update(StoreRequest $request,faq $faq)
    {

        $faq->question_en = $request->input('question_en');
        $faq->question_ar = $request->input('question_ar');
        $faq->answer_en = $request->input('answer_en');
        $faq->answer_ar = $request->input('answer_ar');
        $faq->save();

        return redirect()->route('admin.settings.faq.index')->with('success', 'frequently asked question updated successfully');
    }

    public function destroy(faq $faq)
    {
        $faq->delete();
        return redirect()->route('admin.settings.faq.index')->with('success', 'frequently asked question deleted successfully');
    }
}
